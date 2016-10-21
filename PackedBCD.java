import java.util.*;
/**
* This class stores an arbitrarily long decimal
* as a packed binary coded decimal. Each decimal
* is represented as a nibble. i.e. 51: 0101 0001
* @author Brandon D. Adams
*/
class PackedBCD implements Comparable<PackedBCD> {
    private ArrayList<Byte> mArrayList = new ArrayList<>(0);
	public PackedBCD(){}
	public PackedBCD(int n){ set(n); }
	public PackedBCD(PackedBCD bcd){ set(bcd); }
	public PackedBCD(String strNum){ set(strNum); }
	/**
	* Adds bcd to this
	*/
	public PackedBCD add(PackedBCD bcd){
		byte c = 0, b1 = 0, b2 = 0, sum = 0;
		int size = mArrayList.size()>bcd.mArrayList.size()?mArrayList.size():bcd.mArrayList.size();	
		for(int i=0;i<size*2;++i){
			if(i%2==0){ // Low nibble
				if(i/2<mArrayList.size())
                    b1 = mArrayList.get(i/2); // Grab new byte
                if(i/2<bcd.mArrayList.size())
                    b2 = bcd.mArrayList.get(i/2);
				sum = (byte)((b1&0xF)+(b2&0xF)+c);
				if(sum>9){
					c = 1;
					sum %= 10;
				}else c = 0;
			}else{ // High nibble
				byte n = (byte)(((b1&0xF0)>>4)+((b2&0xF0)>>4)+c); // (bn&0xF0) prevents byte
				if(n>9){                                          // to int sign extention
					c = 1;
					n %= 10;
				}else c = 0;
				sum += n<<4;				
			}
			if((i+1)%2==0){ // Write byte
				if(i/2<mArrayList.size())
					mArrayList.set(i/2,sum);
				else mArrayList.add(sum);
				b1 = 0;
				b2 = 0;
			}			
		}
		if(c!=0) mArrayList.add(c); // Append carry bit
		return this;
	}
	/**
	* Compares this to bcd
	* @param bcd Compared to the calling object
	* @return {@code if(this<bcd) return -1; if(this==bcd) return 0; else 1}
	*/
	public int compareTo(PackedBCD bcd){
		if(mArrayList.size()<bcd.mArrayList.size()) return -1;
		if(mArrayList.size()>bcd.mArrayList.size()) return 1;
		byte b1 = 0, b2 = 0;
		for(int i=0;i<mArrayList.size()*2;++i){
			if(i%2==0){ // High nibble
				b1 = mArrayList.get(mArrayList.size()-1-i/2);
                b2 = bcd.mArrayList.get(mArrayList.size()-1-i/2);
				if((b1&0xF0)<(b2&0xF0)) return -1;
				if((b1&0xF0)>(b2&0xF0)) return 1; 
			}else{ // Low nibble
				if((b1&0x0F)<(b2&0x0F)) return -1;
				if((b1&0x0F)>(b2&0x0F)) return 1;	
			}
		}		
		return 0;
	}
	/**
	* Returns PackedBCD number as a String
	*/
	public String getString(){
		ListIterator<Byte> iter = mArrayList.listIterator(mArrayList.size());
        byte b = 0;
		String str = "";
        	for(int i=0;i<mArrayList.size()*2;++i){
                if(i%2==0){
                	b = iter.previous();
                	if(i==0&&((b&0xF0)>>4)==0) continue;
                    str += (char)(((b&0xF0)>>4)+48);
                }else{
                    str += (char)((0x0F&b)+48);
                }
        	}
		return str;
	}
	/**
	* Sets this to n
	*/
    public PackedBCD set(int n){
        byte b = 0;
        for(int i=0,m=n;m>0;++i){
            if(i%2==0){
                b = (byte)(m%10);
            }else{
				b += (byte)(m%10<<4);
            }
            m /= 10;
            if((i+1)%2==0){
                mArrayList.add(b);
                b = 0;
            }
        }
        if(b!=0) mArrayList.add(b);
		if(n==0) mArrayList.add((byte)0);
		return this;
    }
    /**
    * Sets this to bcd by copying its contets
    */
    public PackedBCD set(PackedBCD bcd){
        mArrayList = new ArrayList<Byte>(bcd.mArrayList);
        return this;
    }
	/**
	* Sets this to the number represented by strNum in base 10
	* @param strNum A number as a string i.e. "12345" not "12f" or "012" or "00"
	* @throws NumberFormatException If the string does not represent a number
	*/
	public PackedBCD set(String strNum) throws NumberFormatException {
		byte b = 0;
		for(int i=strNum.length()-1;i>-1;--i){
			char c = strNum.charAt(i);
			if(c>47&&c<58){
				if((strNum.length()-1-i)%2==0)
					b = (byte)(c-48);
				else b += (byte)(c-48<<4);
			}else throw new NumberFormatException(strNum);
			if((strNum.length()-i)%2==0){
				mArrayList.add(b);
				b = 0;
			}
		}
		if(b!=0||strNum.length()==1&&strNum.charAt(0)=='0') // "0" ok
			mArrayList.add(b);
		else if(strNum.length()>1&&strNum.charAt(0)=='0') // "00" not ok
			throw new NumberFormatException(strNum);
		return this;
	}
	/**
	* Print number as a string
	*/
    public PackedBCD print(){
		System.out.println(getString());
		return this;
    }
	/**
	* Print the bytes making up PackedBCD
	*/
	public PackedBCD printBytes(){
		ListIterator<Byte> iter = mArrayList.listIterator(mArrayList.size());
		while(iter.hasNext())
			System.out.print((byte)iter.previous() + " ");
		System.out.println();
		return this;
	}
	/**
	* Clears PackedBCD
	*/
	public PackedBCD clear(){
		mArrayList.clear();
		return this;
	}
}

