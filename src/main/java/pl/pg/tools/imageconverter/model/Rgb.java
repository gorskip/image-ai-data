package pl.pg.tools.imageconverter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.beans.ConstructorProperties;

@Data
@AllArgsConstructor
public class Rgb {

    private int red;
    private int green;
    private int blue;

    public String getHex() {
       return String.format("#%02x%02x%02x", red, green, blue);
    }

}
