public class Main {
    RNAFolding rnaFolding = new RNAFolding();
    public static void main(String[] args) {
        String rna = "AUGCUAGU";
        System.out.println(rna.length());
        System.out.println("RNA Sequence: " + rna);
        System.out.println("Folded: " + RNAFolding.foldRNA(rna));

        String advrna = "AUGCUAGU";
        RNAFoldingAdv rnaFoldingAdv = new RNAFoldingAdv(); // Create an instance
        System.out.println("RNA Sequence: " + advrna);
        System.out.println("Folded: " + rnaFoldingAdv.foldRNA(advrna));
    }
}