package fi.helsinki.cs.okkopa.reference;

public class Reference {

    private final ReferenceNumber number;
    private final Integer size;
    private final ReferenceString letters;

    public Reference() {
        this(6);
    }

    public Reference(int size) {
        this.size = size;

        this.number = new ReferenceNumber(size);
        this.letters = new ReferenceString(size);
    }

    public boolean checkReferenceNumber(int number) {
        return this.number.checkReferenceNumber(number);
    }

    public int getReferenceNumber() {
        return this.number.getReferenceNumber();
    }

    public String getReference() {
        return this.letters.getReference();
    }

    public boolean checkReference(String reference) {
        return this.letters.checkReference(reference);
    }
}
