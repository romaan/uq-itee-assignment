/*
** TimerExample
** 
** The program adds strings from standard input to a list and then removes
** them some number of seconds later. The time delay is given as the first
** (and only) command line argument.
*/

import java.util.*;
import java.io.*;

public class TimerExample {
    protected Vector requestList;
    protected Timer timer;
    protected int delay; // seconds

    class Element extends TimerTask {
	private Object data;

	public Element(Object o) {
	    data = o;
	}

	// The run() method is called at the scheduled time
	public void run() {
	    System.out.println("Timeout - removing: " + data);
	    requestList.remove(this);
	}
	    
	// We want elements to be compared based on the data they contain, 
	// so we override the equals() method
	public boolean equals(Object o) {
	    return (o != null) && (this.getClass() == o.getClass()) &&
		data.equals(((Element)o).data);
	}

	// If we convert this object to a string, we really want to do it 
	// for the data
	public String toString() {
	    return data.toString();
	}
    }

    // Constructor
    public TimerExample(int d) {
	requestList = new Vector();
	timer = new Timer(true); // true = daemon - don't prolong this process
	delay = d; // seconds
    }

    // Add an object to the list and schedule its deletion. (Time is
    // multiplied by 1000 because the schedule() method expects milliseconds)
    void add(Object o) {
	System.out.println("Adding " + o);
	Element e = new Element(o);
	requestList.add(e);
	timer.schedule(e, delay * 1000);
    }

    // Remove object from list 
    void remove(Object o) {
	int position = requestList.indexOf(new Element(o));
	if(position != -1) {
	    // Found element in list - remove it and cancel the scheduled
	    // removal
	    System.out.println("Manually removing " + o);
	    Element e = (Element)requestList.remove(position);
	    e.cancel();
	} else {
	    System.out.println("Not found: " + o);
	}
    }


    public static void main(String [] args) throws Exception {
	if(args.length != 1) {
			System.out.println("Usage: java " + "TimerExample" + " " + "delay");
			System.exit(-1);
		}
	// Assume first argument is number of seconds to delay
	TimerExample te = new TimerExample(Integer.parseInt(args[0]));

	BufferedReader stdin = 
		new BufferedReader(new InputStreamReader(System.in));
	String userInput;

	// Read lines from standard input (until end of file)
	// If line is "d", read another line and delete the first entry
	// of that type from the list, otherwise add the entry to the list
	while((userInput = stdin.readLine()) != null) {
	    if(userInput.equals("d")) {
		// We're going to delete an entry - read another line
		userInput = stdin.readLine();
		te.remove(userInput);
	    } else {
		te.add(new String(userInput));
	    }
	}
    }
}
