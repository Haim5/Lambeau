import java.util.*;

public class Facade {
    public Facade() { }

    public void findSolution() {

        LinkedList<Package> l  = new LinkedList<>();
        l.add(new Package(20, 10,5));//0
        l.add(new Package(15,10,5));//1
        l.add(new Package(5,20,5));//2
        l.add(new Package(15,10,10));//3
        l.add(new Package(5,5,5));//4
        l.add(new Package(20,20,10));//5
        LinkedList<Solution> solutions = new LinkedList<>();
        Bin b = new Bin(20,20,20);


        for(int i = 5; i <= 15; i++) {

            solutions.add(this.find(new BFD(new RSMC()), new ClusteredAreaHeightSorter(new Bin(b), i), new Bin(b), l));
        }

        for(int i = 21; i <= 24; i++) {

            solutions.add(this.find(new BFD(new RSMC()), new ClusteredHeightAreaSorter(new Bin(b), i), new Bin(b), l));
        }
        for(int i = 50; i <= 57; i++) {

            solutions.add(this.find(new BFD(new RSMC()), new ClusteredHeightAreaSorter(new Bin(b), i), new Bin(b), l));
        }
        for(int i = 15; i <= 25; i++) {

            solutions.add(this.find(new FFD(), new ClusteredHeightAreaSorter(new Bin(b), i), new Bin(b), l));
        }
        for(int i = 50; i <= 60; i++) {

            solutions.add(this.find(new FFD(), new ClusteredHeightAreaSorter(new Bin(b), i), new Bin(b), l));
        }
        for(int i = 5; i <= 25; i++) {

            solutions.add(this.find(new FFD(), new ClusteredAreaHeightSorter(new Bin(b), i), new Bin(b), l));
        }



        int count = 0;
        for(Solution sol : solutions) {
            if(sol.unpacked() == 0 && sol.numOfBins() == 1) {
                this.printSolution(sol, count);
            }
            count++;
        }
    }

    private Solution findBest(List<Solution> sol) {
        if(sol == null || sol.size() == 0) {
            return null;
        }
        Solution best = sol.get(0);
        int len = sol.size();
        for(int i =1; i < len; i++) {
            Solution temp = sol.get(i);
            if(temp.isBetter(best)) {
                best = temp;
            }
        }
        return best;
    }



    private Solution find(Placer placer, Sorter sorter, Bin b, List<Package> packages) {
        Packer ar = new Packer(placer, sorter, b, packages);
        ar.pack();
        return ar.getSolution();
    }

    private void printSolution(Solution s, int i) {
        if(s.unpacked() > 0) {
            System.out.println("Unpacked Packages:\n");
            List<Package> un = s.getUnpacked();
            for(Package unpacked : un) {
                System.out.println(unpacked);
            }
            System.out.println("\n");
        }

        System.out.println("Packed Packages:\n");
        List<Bin> bins = s.getBins();
        int num = 1;
        for(Bin b22 : bins) {
            System.out.println("Bin " + num + ":\n");
            num++;
            System.out.println(b22);
            System.out.println("\n");
        }

        System.out.println("\n");
        System.out.println("Unpacked: " + s.unpacked() + ". Bins: " + s.numOfBins() + ". Found at: " + i + ".\n");
    }

}
