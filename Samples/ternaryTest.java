class ternaryTest {
    public static void main(String[] args) {
        System.out.println(new ternTest().testCases());
    }
}

class ternTest {

    int iField;
    boolean bField;

    public int testCases() {
        iField = iField + this.truePredicate();
        iField = iField + this.falsePredicate();
        iField = iField + this.nestedTernary();
        return iField;
    }

    public int truePredicate() {
        bField = true;
        return (bField) ? 1 : 0;
    }

    public int falsePredicate() {
        bField = false;
        return (bField) ? 0 : 1;
    }

    public int nestedTernary() {
        int iLocal;
        boolean bLocal;
        iLocal = 0;
        bLocal= true;
        bField = true;
        if ( (bField && bLocal) ? true : false) {
            iLocal = 1;
        } else {
            iLocal = 0;
        }
        return iLocal;
    }
}
