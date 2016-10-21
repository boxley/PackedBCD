/**
* Tests for PackedBCD
*/
class PackedBCDTest {
	public static void main(String[] args){
		try{
			assert ((new PackedBCD(1)).print().getString().compareTo("1")==0);
			assert ((new PackedBCD("1")).print().getString().compareTo("1")==0);
			assert ((new PackedBCD(1234567890)).print().getString().compareTo("1234567890")==0);
			assert ((new PackedBCD("12345678901234567890")).print().getString().compareTo("12345678901234567890")==0);
			try{
				PackedBCD bcd = new PackedBCD();
				bcd.set("1234G567890"); // Throws NumberFormatException
			}catch(NumberFormatException e){
				System.out.println("Correctly threw: " + e);
			}
			try{
                	        PackedBCD bcd = new PackedBCD();
                	        bcd.set("00"); // So does this one
                	}catch(NumberFormatException e){
                	        System.out.println("Correctly threw: " + e);
                	}
			try{
                	        PackedBCD bcd = new PackedBCD();
                	        bcd.set("000"); // And this one
                	}catch(NumberFormatException e){
                	        System.out.println("Correctly hrew: " + e);
                	}
			assert ((new PackedBCD(1)).add(new PackedBCD(1)).print().getString().compareTo("2")==0);
			assert ((new PackedBCD("9")).add(new PackedBCD("9")).print().getString().compareTo("18")==0);
			assert ((new PackedBCD(999)).add(new PackedBCD(9)).print().getString().compareTo("1008")==0);
			assert ((new PackedBCD(9)).add(new PackedBCD(999)).print().getString().compareTo("1008")==0);
			assert ((new PackedBCD(10999)).add(new PackedBCD(999)).print().getString().compareTo("11998")==0);
			assert (new PackedBCD(0).print().getString().compareTo("0")==0);
			assert (new PackedBCD("0").print().getString().compareTo("0")==0);
			
			assert (new PackedBCD(1)).compareTo(new PackedBCD("1"))==0;
			assert (new PackedBCD(1234567890)).compareTo(new PackedBCD("1234567890"))==0;
			assert (new PackedBCD("1234")).compareTo(new PackedBCD(1235))==-1;
			assert (new PackedBCD("2")).compareTo(new PackedBCD(1))==1;
			assert (new PackedBCD("8")).compareTo(new PackedBCD(9))==-1;
			assert (new PackedBCD("9")).compareTo(new PackedBCD(8))==1;
			assert (new PackedBCD("1235")).compareTo(new PackedBCD(1234))==1;
			assert (new PackedBCD("123")).compareTo(new PackedBCD(1235))==-1;
			assert (new PackedBCD("1234")).compareTo(new PackedBCD(123))==1;

			assert (new PackedBCD(new PackedBCD(991))).compareTo(new PackedBCD("991"))==0;
			
		}catch(Throwable e){
			System.out.println("Threw error: " + e);
		}
	}
}
