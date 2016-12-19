package org.baumandm.monglorious;

/**
 * Monglorious Exception base class.
 *
 * Created by baumandm on 12/18/16.
 */
public class MongloriousException extends Exception {

    public MongloriousException() { }

    public MongloriousException(String message) {
        super(message);
    }

    public MongloriousException(String message, Throwable ex) {
        super(message, ex);
    }

    public MongloriousException(Throwable ex) {
        super(ex);
    }

}
