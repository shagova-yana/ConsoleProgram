package com.company.unique;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UniqlaucherTest {

    private boolean assertFile(String file1, String file2) throws IOException {
        try (BufferedReader expected= new BufferedReader(new FileReader(file1))) {
            try (BufferedReader actual= new BufferedReader(new FileReader(file2))) {
                int expectedSize = 0;
                int actualSize = 0;
                int count = 0;
                String line = expected.readLine();
                String str = actual.readLine();
                do {
                    if (line != null) expectedSize++;
                    if (str != null) actualSize++;
                    if (Objects.equals(line, str)) count++;
                    line = expected.readLine();
                    str = actual.readLine();
                } while (line != null || str != null);
                return count == expectedSize && count == actualSize;
            }
        }
    }

    @Test
    public void test() throws IOException {
        String[] args = {"-i", "-c", "-o", "file1.txt", "src\\test\\resources\\input.txt"};
        Uniqlaucher.main(args);
        assertTrue(assertFile("src\\test\\resources\\result\\res1.txt", "src\\test\\resources\\file1.txt"));

        args = new String[] {"-c", "-s", "3", "-o", "file2.txt", "src\\test\\resources\\input.txt"};
        Uniqlaucher.main(args);
        assertTrue(assertFile("src\\test\\resources\\result\\res2.txt", "src\\test\\resources\\file2.txt"));

        args = new String[] {"-i", "-c", "-s", "3", "-o", "file3.txt", "src\\test\\resources\\input.txt"};
        Uniqlaucher.main(args);
        assertTrue(assertFile("src\\test\\resources\\result\\res3.txt", "src\\test\\resources\\file3.txt"));

        args = new String[] {"-i", "-u", "-o", "file4.txt", "src\\test\\resources\\input.txt"};
        Uniqlaucher.main(args);
        assertTrue(assertFile("src\\test\\resources\\result\\res4.txt", "src\\test\\resources\\file4.txt"));

        args = new String[] {"-i", "-u", "-s", "3", "-o", "file5.txt", "src\\test\\resources\\input.txt"};
        Uniqlaucher.main(args);
        assertTrue(assertFile("src\\test\\resources\\result\\res5.txt", "src\\test\\resources\\file5.txt"));

        args = new String[] {"-i", "-u", "src\\test\\resources\\input.txt"};
        Uniqlaucher.main(args);


    }

    @Test
    public void exceptionTest()  {

        String [] args = new  String[] {"-i", "-u", "outFile2.txt", "input"};
        String[] finalArgs1 = args;
        assertThrows(IOException.class, () -> Uniqlaucher.main(finalArgs1));

        args = new String[]{"-c", "-s", "3", "-o", "outfile3.txt", "input.rle"};
        String[] finalArgs2 = args;
        assertThrows(IOException.class, () -> Uniqlaucher.main(finalArgs2));

        args = new String[]{"-c", "-s", "-1", "-o", "outfile4.txt", "src\\test\\resources\\input.txt"};
        String[] finalArgs3 = args;
        assertThrows(IOException.class, () -> Uniqlaucher.main(finalArgs2));
    }
}