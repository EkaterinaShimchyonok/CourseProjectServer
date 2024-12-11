package org.example.CommandPattern;

import java.io.PrintWriter;

public interface Command {
    void execute(String param, PrintWriter out);
}
