package com.votingresult.Model;

/**
 * Created by Aspire on 12/27/2017.
 */

public class LoginModel {

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public boolean isKing() {
            return king;
        }

        public void setKing(boolean king) {
            this.king = king;
        }

        public boolean isQueen() {
            return queen;
        }

        public void setQueen(boolean queen) {
            this.queen = queen;
        }

        public boolean isPrincess() {
            return princess;
        }

        public void setPrincess(boolean princess) {
            this.princess = princess;
        }

        public boolean isPrince() {
            return prince;
        }

        public void setPrince(boolean prince) {
            this.prince = prince;
        }

        public boolean isMaster() {
            return master;
        }

        public void setMaster(boolean master) {
            this.master = master;
        }

        public boolean isMiss() {
            return miss;
        }

        public void setMiss(boolean miss) {
            this.miss = miss;
        }

        String random;
        boolean king=false;
        boolean queen=false;
        boolean princess=false;
        boolean prince=false;
        boolean master=false;
        boolean miss=false;
}
