package fi.helsinki.cs.web;

import java.util.ArrayList;
import java.util.List;

public class References {
    private static List<Ref> references = new ArrayList();

    public References() {
    }

    public List<Ref> getReferences() {
        return references;
    }

    public void addReference(Ref ref) {
        this.references.add(ref);
    }    
}
