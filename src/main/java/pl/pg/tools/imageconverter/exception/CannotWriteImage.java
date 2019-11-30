package pl.pg.tools.imageconverter.exception;

public class CannotWriteImage extends RuntimeException {

    public CannotWriteImage(String message, Throwable cause) {
        super(message, cause);
    }
}
