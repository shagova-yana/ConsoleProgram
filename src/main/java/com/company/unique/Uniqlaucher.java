package com.company.unique;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;

class Uniqlaucher {

    @Option(name = "-i", metaVar = "register", usage = "Register")
    private boolean register;

    @Option(name = "-u", metaVar = "unique", usage = "Unique line output")
    private boolean uniqStr;

    @Option(name = "-c", metaVar = "count", usage = "Display the number of replaced row strings")
    private boolean countReplace;

    @Option(name = "-s", metaVar = "symbol", usage = "Type of task")
    private int countSymbol;

    @Option(name = "-o", metaVar = "OutputName", usage = "Output file name")
    private String outputName;

    @Argument(metaVar = "InputName", usage = "Input file name")
    private String inputName;

    public void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar Uniq.jar [-i] [-u] [-c] [-s num] [-o ofile] [file]");
            parser.printUsage(System.err);
        }
    }

    public static void main(String[] args) throws IOException {
        Uniqlaucher a = new Uniqlaucher();
        a.launch(args);
        Uniq uniq = new Uniq();
        try {
            if (a.countSymbol < 0) {
                System.err.println("ERROR: The values of the -s flag are not correct.");
                throw new IllegalArgumentException();
            }
            if (a.uniqStr) {
                uniq.uniqueLine(a.register, a.countSymbol, a.outputName, a.inputName);
                System.out.println("Success");
            }
            else {
                if (a.countReplace){
                    uniq.repeatLine(a.register, a.countSymbol, a.outputName, a.inputName);
                    System.out.println("Success");
                }
               else System.err.println("ERROR: Can't define the task.");
            }
        }
        catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
            throw e;
        }
    }
}
