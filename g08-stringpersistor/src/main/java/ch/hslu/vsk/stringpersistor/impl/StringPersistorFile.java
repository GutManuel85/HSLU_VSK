package ch.hslu.vsk.stringpersistor.impl;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 * g08-logger
 * <p>
 *     A Class to save Strings on a Files.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public final class StringPersistorFile implements StringPersistor {

    private File file;


    @Override
    public void setFile(final File file) {
        this.file = file;
    }

    @Override
    public void save(final Instant timestamp, final String payload) {

        if (timestamp == null || payload == null) {
            throw new IllegalArgumentException();
        }
        String timeStampString = DateTimeFormatter.ISO_INSTANT.format(timestamp);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(file, true), StandardCharsets.UTF_8));
            bufferedWriter.write(timeStampString);
            bufferedWriter.write("  |  ");
            bufferedWriter.write(payload);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public List<PersistedString> get(final int count) {
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            List<PersistedString> list = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                String[] split = line.split("\\s\\s\\|\\s\\s", 2);
                String timeString = split[0];
                String payload = split[1];
                if (payload == null || timeString == null){
                    break;
                }
                Instant timestamp = Instant.parse(timeString);
                list.add(new PersistedString(timestamp, payload));
            }
            bufferedReader.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}