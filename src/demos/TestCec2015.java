package demos;
import java.io.*;
import java.util.Scanner;

import problems.cec2015.testfunc;

public class TestCec2015 {

	public static void testmain() throws Exception {
		int i,j,k,n,m,func_num;
		double[] f,x;
		File fpt;
		
		m=5;
		n=30;
		
		x = new double[m*n];
		f = new double[m];
		
		testfunc tf = new testfunc();
		 
		for (i=0;i<15;i++)
		{
			func_num = i+1;
			fpt = new File("input_data/shift_data_"+func_num+".txt");
			Scanner input = new Scanner(fpt);
			
			for(k=0;k<n;k++)
			{
				x[k] = input.nextDouble();
			}
			
			for(int ii=0;ii<n;ii++){
				//System.out.println(x[i]);
			}
			
			input.close();
			
			
			for(j=0;j<n;j++)
			{
				x[1*n+j] = 0.0;
				//System.out.println(x[1*n+j]);
			}

			for (k=0;k<1;k++)
			{
				tf.test_func(x,f,n,m,func_num);
				for(j=0;j<m;j++)
				{
					System.out.println("f"+func_num+"(x["+(j+1)+"])="+f[j]);
				}
			}
			input.close();
		}
	}

	public static void mytestmain() throws Exception {
		int i,j,k,n,m,func_num;
		double[] f,x;
		File fpt;
		
		m=5;
		n=30;
		
		x = new double[m*n];
		f = new double[m];
		
		testfunc tf = new testfunc();
		 
		for (i=0;i<15;i++)
		{
			func_num = i+1;
			fpt = new File("input_data/shift_data_"+func_num+".txt");
			Scanner input = new Scanner(fpt);
			
			for(k=0;k<n;k++)
			{
				x[k] = input.nextDouble();
			}
			
			input.close();
			
			for (k=0;k<1;k++)
			{
				tf.test_func(x,f,n,m,func_num);
				for(j=0;j<m;j++)
				{
					System.out.println("f"+func_num+"(x["+(j+1)+"])="+f[j]);
				}
			}
			input.close();
		}
	}

	public static void main(String[] args) throws Exception {
		mytestmain();
		
		
	}
}


