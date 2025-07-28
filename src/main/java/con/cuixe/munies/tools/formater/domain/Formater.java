package con.cuixe.munies.tools.formater.domain;

import con.cuixe.munies.tools.formater.FileFormat;

import java.io.InputStream;

public interface Formater {

    FileFormat getFormat();

    String format(String input, String output) throws Exception;

    String format(InputStream input, String output) throws Exception;
}
