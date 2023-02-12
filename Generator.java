
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class Generator {
    Document document;
    
    public Generator(List libri) throws ParserConfigurationException {
        createDOMTree(libri);
    }
    
    private void createDOMTree(List libri) throws ParserConfigurationException {
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Element root;
        Iterator iterator;
        Libro libro;
        Element element;
        
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.newDocument();
        root = document.createElement("libri");
        document.appendChild(root);
        // per ogni oggetto Libro crea un nodo figlio del nodo radice
        iterator = libri.iterator();
        while (iterator.hasNext()) {
            libro = (Libro)iterator.next();
            element = createLibroElement(libro);
            root.appendChild(element);
        }
    }
    
    // metodo ausiliario per la creazione di un elemento XML Libro
    private Element createLibroElement(Libro libro) {
        Text text;
        Element book = document.createElement("libro");
        book.setAttribute("genere", libro.getGenere());
        Element author = document.createElement("autore");
        text = document.createTextNode(libro.getAutore());
        author.appendChild(text);
        book.appendChild(author);
        Element title = document.createElement("titolo");
        text = document.createTextNode(libro.getTitolo());
        title.appendChild(text);
        book.appendChild(title);
        Element price = document.createElement("prezzo");
        text = document.createTextNode(Float.toString(libro.getPrezzo()));
        price.appendChild(text);
        book.appendChild(title);
        return book;
    }
    
    private void printToFile(String filename) throws TransformerException {
        TransformerFactory factory;
        Transformer transformer;
        DOMSource source;
        StreamResult stream;

        factory = TransformerFactory.newInstance();
        transformer = factory.newTransformer();
        source = new DOMSource(document);
        stream = new StreamResult(new File(filename));
        transformer.transform(source, stream);
    }
    
    public static void main(String args[]) {
        Libro libro;
        Generator xml;
        List libri = new ArrayList();

        // inizializzazione della lista
        libro = new Libro("fantasy", "The Hobbit", "Tolkien", 15);
        libri.add(libro);
        libro = new Libro("fantasy", "The Lord of rings", "Tolkien", 25);
        libri.add(libro);
        try {
            xml = new Generator(libri);
            xml.printToFile(args[0]);
        }
        catch (ParserConfigurationException | TransformerException exception) {
            System.out.println("Errore!");
        }
    }
}
