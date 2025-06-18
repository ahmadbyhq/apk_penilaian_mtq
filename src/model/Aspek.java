package model;

    public class Aspek {
        private int id_aspek;
        private int id_lomba;
        private String nama_aspek;
        private double presentase;

        public Aspek(int id_aspek, int id_lomba, String nama_aspek, double presentase) {
            this.id_aspek = id_aspek;
            this.id_lomba = id_lomba;
            this.nama_aspek = nama_aspek;
            this.presentase = presentase;
        }

        public int getid_aspek() {
            return id_aspek;
        }

        public int getid_lomba() {
            return id_lomba;
        }

        public String getnama_aspek() {
            return nama_aspek;
        }

        public double getPresentase() {
            return presentase;
        }

        @Override
        public String toString() {
            return nama_aspek + " (" + presentase + "%)";
        }
}
