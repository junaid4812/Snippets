package com.lazy.files.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class CombinedPrintStream extends PrintStream {

    //how to use this combined print stream
    /*
    try (PrintStream console = System.out;
         PrintStream fileStream = new PrintStream(new FileOutputStream(outFile, false));
         CombinedPrintStream combinedStream = new CombinedPrintStream(console, fileStream)) {
            System.setOut(combinedStream); // Redirect System.out to combinedStream
            … add your logic here… System.out will perform below println or print. If you want printf then override here
    } catch (IOException e) {
        e.printStackTrace();
    }
    */

    private PrintStream consoleStream;
    private PrintStream fileStream;

    public CombinedPrintStream(PrintStream consoleStream, PrintStream fileStream) {
        super(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // Do nothing, this stream is used for redirection
            }
        });
        this.consoleStream = consoleStream;
        this.fileStream = fileStream;
    }

    @Override
    public void println(String x) {
        consoleStream.println(x);
        fileStream.println(x);
        consoleStream.flush();
        fileStream.flush();
    }
    @Override
    public void print(String s) {
        consoleStream.print(s);
        fileStream.print(s);
        consoleStream.flush();
        fileStream.flush();
    }

    @Override
    public void close() {
        // Closing the combined stream should not close the underlying streams
        consoleStream.close();
        fileStream.close();
    }
}