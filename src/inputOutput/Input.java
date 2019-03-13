package inputOutput;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Set;

public class Input {

    private int seed;
    private int numberOfAgents;
    private double probSociable;
    private double probMeeting;
    private double probInfection;
    private double probRecovery;
    private double mortality;
    private int numberOfDays;
    private int meanFriends;
    private Path fileWithReport;

    public void processInput() {

        URL pathURL = Thread.currentThread().getContextClassLoader().getResource("default.properties");

        Properties properties = new Properties();

        if (pathURL == null) {
            System.err.println("Brak pliku default.properties");
            System.exit(1);
        }

        try (FileInputStream stream = new FileInputStream(pathURL.getPath());
             Reader reader = Channels.newReader(stream.getChannel(), StandardCharsets.UTF_8.name())) {
            properties.load(reader);
        } catch (MalformedInputException e) {
            System.err.println("default.properties nie jest tekstowy");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Błąd przy wczytywaniu default.properties");
            System.exit(1);
        }

        validationOfDefaultProperties(properties);


        Properties propertiesXML = new Properties();

        pathURL = Thread.currentThread().getContextClassLoader().getResource("simulation-conf.xml");

        if (pathURL == null) {
            System.err.println("Brak pliku simulation-conf.xml");
            System.exit(1);
        }

        try {
            propertiesXML.loadFromXML(new FileInputStream(pathURL.getPath()));
        } catch (InvalidPropertiesFormatException e) {
            System.err.println("simulation-conf.xml nie jest XML");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Błąd przy wczytywaniu simulation-conf.xml");
            System.exit(1);
        }

        Set<String> keys = propertiesXML.stringPropertyNames();

        for (String key : keys) {
            properties.setProperty(key, propertiesXML.getProperty(key));
        }

        validationOfFinalProperties(properties);

    }

    private void validationOfDefaultProperties(Properties properties) {

        String s = properties.getProperty("seed");

        int seed;

        if (s != null) {
            try {
                seed = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza seed");
                System.exit(1);
            }
        }

        s = properties.getProperty("liczbaAgentów");

        int numberOfAgents = 0;

        if (s != null) {
            try {
                numberOfAgents = Integer.parseInt(s);

                if (!(1 <= numberOfAgents && numberOfAgents <= 1e6))
                    throw new NumberFormatException();


            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza liczbaAgentów");
                System.exit(1);
            }
        }

        s = properties.getProperty("prawdTowarzyski");

        double probSociable;

        if (s != null) {
            try {
                probSociable = Double.parseDouble(s);

                if (!(Double.compare(probSociable, 0) >= 0 && Double.compare(probSociable, 1) <= 0))
                    throw new NumberFormatException();

            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza prawdTowarzyski");
                System.exit(1);
            }
        }

        s = properties.getProperty("prawdSpotkania");

        double probMeeting;

        if (s != null) {
            try {
                probMeeting = Double.parseDouble(s);

                if (!(Double.compare(probMeeting, 0) >= 0 && Double.compare(probMeeting, 1) < 0))
                    throw new NumberFormatException();

            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza prawdSpotkania");
                System.exit(1);
            }
        }

        s = properties.getProperty("prawdZarażenia");

        double prawdZarażenia;

        if (s != null) {
            try {
                prawdZarażenia = Double.parseDouble(s);

                if (!(Double.compare(prawdZarażenia, 0) >= 0 && Double.compare(prawdZarażenia, 1) <= 0))
                    throw new NumberFormatException();


            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza prawdZarażenia");
                System.exit(1);
            }
        }

        s = properties.getProperty("prawdWyzdrowienia");

        double prawdWyzdrowienia;

        if (s != null) {
            try {
                prawdWyzdrowienia = Double.parseDouble(s);

                if (!(Double.compare(prawdWyzdrowienia, 0) >= 0 && Double.compare(prawdWyzdrowienia, 1) <= 0))
                    throw new NumberFormatException();


            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza prawdWyzdrowienia");
                System.exit(1);
            }
        }


        s = properties.getProperty("śmiertelność");

        double śmiertelność;

        if (s != null) {
            try {
                śmiertelność = Double.parseDouble(s);

                if (!(Double.compare(śmiertelność, 0) >= 0 && Double.compare(śmiertelność, 1) <= 0))
                    throw new NumberFormatException();


            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza śmiertelność");
                System.exit(1);
            }
        }


        s = properties.getProperty("liczbaDni");

        int liczbaDni;

        if (s != null) {
            try {
                liczbaDni = Integer.parseInt(s);

                if (!(1 <= liczbaDni && liczbaDni <= 1e3))
                    throw new NumberFormatException();

            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza liczbaDni");
                System.exit(1);
            }
        }


        s = properties.getProperty("śrZnajomych");

        int śrZnajomych;

        if (s != null) {
            try {
                śrZnajomych = Integer.parseInt(s);

                if (numberOfAgents != 0 && !(0 <= śrZnajomych && śrZnajomych <= numberOfAgents - 1))
                    throw new NumberFormatException();

            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza śrZnajomych");
                System.exit(1);
            }
        }


        s = properties.getProperty("plikZRaportem");

        if (s != null) {
            try {
                Path plikZRaportem = Paths.get(s);
            } catch (InvalidPathException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza plikZRaportem");
                System.exit(1);
            }
        }


    }

    private void validationOfFinalProperties(Properties properties) {

        String s = properties.getProperty("seed");

        int seed = 0;

        if (s != null) {
            try {
                seed = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza seed");
                System.exit(1);
            }
        } else {
            System.err.println("Brak wartości dla klucza seed");
            System.exit(1);
        }

        this.seed = seed;

        s = properties.getProperty("liczbaAgentów");

        int numberOfAgents = 0;

        if (s != null) {
            try {
                numberOfAgents = Integer.parseInt(s);

                if (!(1 <= numberOfAgents && numberOfAgents <= 1e6))
                    throw new NumberFormatException();


            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza liczbaAgentów");
                System.exit(1);
            }
        } else {
            System.err.println("Brak wartości dla klucza liczbaAgentów");
            System.exit(1);
        }

        this.numberOfAgents = numberOfAgents;

        s = properties.getProperty("prawdTowarzyski");

        double probSociable = 0;

        if (s != null) {
            try {
                probSociable = Double.parseDouble(s);

                if (!(Double.compare(probSociable, 0) >= 0 && Double.compare(probSociable, 1) <= 0))
                    throw new NumberFormatException();

            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza prawdTowarzyski");
                System.exit(1);
            }
        } else {
            System.err.println("Brak wartości dla klucza prawdTowarzyski");
            System.exit(1);
        }

        this.probSociable = probSociable;

        s = properties.getProperty("prawdSpotkania");

        double probMeeting = 0;

        if (s != null) {
            try {
                probMeeting = Double.parseDouble(s);

                if (!(Double.compare(probMeeting, 0) >= 0 && Double.compare(probMeeting, 1) < 0))
                    throw new NumberFormatException();

            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza prawdSpotkania");
                System.exit(1);
            }
        } else {
            System.err.println("Brak wartości dla klucza prawdSpotkania");
            System.exit(1);
        }

        this.probMeeting = probMeeting;

        s = properties.getProperty("prawdZarażenia");

        double prawdZarażenia = 0;

        if (s != null) {
            try {
                prawdZarażenia = Double.parseDouble(s);

                if (!(Double.compare(prawdZarażenia, 0) >= 0 && Double.compare(prawdZarażenia, 1) <= 0))
                    throw new NumberFormatException();


            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza prawdZarażenia");
                System.exit(1);
            }
        } else {
            System.err.println("Brak wartości dla klucza prawdZarażenia");
            System.exit(1);
        }

        this.probInfection = prawdZarażenia;

        s = properties.getProperty("prawdWyzdrowienia");

        double prawdWyzdrowienia = 0;

        if (s != null) {
            try {
                prawdWyzdrowienia = Double.parseDouble(s);

                if (!(Double.compare(prawdWyzdrowienia, 0) >= 0 && Double.compare(prawdWyzdrowienia, 1) <= 0))
                    throw new NumberFormatException();


            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza prawdWyzdrowienia");
                System.exit(1);
            }
        } else {
            System.err.println("Brak wartości dla klucza prawdWyzdrowienia");
            System.exit(1);
        }

        this.probRecovery = prawdWyzdrowienia;


        s = properties.getProperty("śmiertelność");

        double śmiertelność = 0;

        if (s != null) {
            try {
                śmiertelność = Double.parseDouble(s);

                if (!(Double.compare(śmiertelność, 0) >= 0 && Double.compare(śmiertelność, 1) <= 0))
                    throw new NumberFormatException();


            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza śmiertelność");
                System.exit(1);
            }
        } else {
            System.err.println("Brak wartości dla klucza śmiertelność");
            System.exit(1);
        }

        this.mortality = śmiertelność;


        s = properties.getProperty("liczbaDni");

        int liczbaDni = 0;

        if (s != null) {
            try {
                liczbaDni = Integer.parseInt(s);

                if (!(1 <= liczbaDni && liczbaDni <= 1e3))
                    throw new NumberFormatException();

            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza liczbaDni");
                System.exit(1);
            }
        } else {
            System.err.println("Brak wartości dla klucza liczbaDni");
            System.exit(1);
        }

        this.numberOfDays = liczbaDni;


        s = properties.getProperty("śrZnajomych");

        int śrZnajomych = 0;

        if (s != null) {
            try {
                śrZnajomych = Integer.parseInt(s);

                if (!(0 <= śrZnajomych && śrZnajomych <= numberOfAgents - 1))
                    throw new NumberFormatException();

            } catch (NumberFormatException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza śrZnajomych");
                System.exit(1);
            }
        } else {
            System.err.println("Brak wartości dla klucza śrZnajomych");
            System.exit(1);
        }

        this.meanFriends = śrZnajomych;

        s = properties.getProperty("plikZRaportem");
        Path plikZRaportem = null;

        if (s != null) {
            try {
                plikZRaportem = Paths.get(s);
            } catch (InvalidPathException e) {
                System.err.println("Niedozwolona wartość " + s + " dla klucza plikZRaportem");
                System.exit(1);
            }
        } else {
            System.err.println("Brak wartości dla klucza plikZRaportem");
            System.exit(1);
        }

        this.fileWithReport = plikZRaportem;
    }


    public int getSeed() {
        return seed;
    }

    public int getNumberOfAgents() {
        return numberOfAgents;
    }

    public double getProbSociable() {
        return probSociable;
    }

    public double getProbMeeting() {
        return probMeeting;
    }

    public double getProbInfection() {
        return probInfection;
    }

    public double getProbRecovery() {
        return probRecovery;
    }

    public double getMortality() {
        return mortality;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public int getMeanFriends() {
        return meanFriends;
    }

    public Path getFileWithReport() {
        return fileWithReport;
    }

    @Override
    public String toString() {
        return this.outToString() + "\nplikZRaportem=" + fileWithReport;
    }

    public String outToString() {
        return "seed=" + seed +
                "\nliczbaAgentów=" + numberOfAgents +
                "\nprawdTowarzyski=" + probSociable +
                "\nprawdSpotkania=" + probMeeting +
                "\nprawdZarażenia=" + probInfection +
                "\nprawdWyzdrowienia=" + probRecovery +
                "\nśmiertelność=" + mortality +
                "\nliczbaDni=" + numberOfDays +
                "\nśrZnajomych=" + meanFriends;
    }
}
