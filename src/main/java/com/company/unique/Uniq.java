package com.company.unique;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Uniq {
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> resultList = new ArrayList<>();

    private void readLine(String inputName) throws IOException {
        BufferedReader reader;
        if (inputName == null) {
            InputStreamReader in = new InputStreamReader(System.in);
            reader = new BufferedReader(in);
        } else {
            FileReader in = new FileReader(inputName);
            reader = new BufferedReader(in);
        }
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        reader.close();
    }

    private void writeLine(String outputName, boolean f, String inputName) throws IOException {
        if (outputName == null)
            for (String line : resultList)
                System.out.println(line);
        else {
            OutputStreamWriter output;
            if (inputName == null) output = new OutputStreamWriter(new FileOutputStream(outputName));
            else {
                Path in = Paths.get(inputName);
                String o = in.getParent() + File.separator + outputName;
                output = new OutputStreamWriter(new FileOutputStream(o));
            }
            BufferedWriter writer = new BufferedWriter(output);
            for (String str : resultList){
                writer.write(str);
                if (resultList.indexOf(str) < resultList.size() - 1)
                writer.newLine();
            }
            writer.close();
            output.close();
        }
    }

    private boolean condition(boolean register, String line, String str, int countSymbol) {
        try {
            return register && line.substring(countSymbol).equalsIgnoreCase(str.substring(countSymbol))
                    || !register && line.substring(countSymbol).equals(str.substring(countSymbol));
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.err.println("ERROR: Can't define the task.");
        }
        return true;
    }

    public void uniqueLine(boolean register, int countSymbol, String outputName, String inputName) throws IOException {
        readLine(inputName);
        // обработка
        for (String line : list){
            int count = 0;
            for (int i = list.indexOf(line) + 1; i < list.size(); i++)
                if (condition(register, line, list.get(i), countSymbol))
                    count++;
            for (int i = list.indexOf(line) - 1; i > -1; i--)
                if (condition(register, line, list.get(i), countSymbol))
                    count++;
            if (count == 0) resultList.add(line);
        }
        writeLine(outputName, false, inputName);
    }

    public void repeatLine(boolean register, int countSymbol, String outputName, String inputName) throws IOException {
        readLine(inputName);
        // обработка
        int i = 0;
        while (i < list.size() - 1){
            int count = 1;
            String line = list.get(i);
            if (condition(register, list.get(i), list.get(i + 1), countSymbol))
                do {
                    i++;
                    count++;
                    if (i == list.size()) break;
                } while (list.get(i).equalsIgnoreCase(list.get(i + 1)));
            resultList.add(count + " " + line);
            i++;
        }
        if (i == list.size() - 1) resultList.add("1" + " " + list.get(i));
        writeLine(outputName, true, inputName);
    }
}

