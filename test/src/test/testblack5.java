package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class testblack5 {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws Exception{
		String file="test.txt";
        BufferedReader input = new BufferedReader(new FileReader(file));
        FileWriter output = new FileWriter(("shuchu.txt"));
        String s = input.readLine();
        String h = "";
        while (h != null) {
            s = s + " " + h;
            h = input.readLine();
        }
        String[] result = s.toLowerCase().split("[^a-z]++");
        for (String aa : result) {
            System.out.println(aa);
        }
        int i = 0;
        String[] flag = new String[result.length];
        int[][] d = new int[result.length][result.length];
        List<String> list = new ArrayList<String>();
        for (String aa : result) {
            if (list.contains(aa) == false) {
                flag[i] = aa;
                i++;
                list.add(aa);
            }
        }
        for (i = 0; i < result.length - 1; i++) {
            d[list.indexOf(result[i])][list.indexOf(result[i + 1])]++;
        }
        chuli cl = new chuli(flag, d, list);
        int biaoji=0;
		String result3 = cl.generateNewText("new and");
		assertEquals("new life and"+"\n", result3);
		
	}

}
