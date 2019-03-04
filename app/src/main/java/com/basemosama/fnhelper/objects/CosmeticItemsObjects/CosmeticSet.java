package com.basemosama.fnhelper.objects.CosmeticItemsObjects;

import java.util.List;

public class CosmeticSet{
    private String name;
    private List<CosemticEntries> entries;

    public CosmeticSet(String name, List<CosemticEntries> entries) {
        this.name = name;
        this.entries = entries;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEntries(List<CosemticEntries> entries) {
        this.entries = entries;
    }

    public String getName() {
        return name;
    }

    public List<CosemticEntries> getEntries() {
        return entries;
    }

    public class CosemticEntries{
        private String identifier;
        private String name;
        private String type;
        private long lastupdate;

        public CosemticEntries(String identifier, String name, String type, long lastupdate) {
            this.identifier = identifier;
            this.name = name;
            this.type = type;
            this.lastupdate = lastupdate;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setLastupdate(long lastupdate) {
            this.lastupdate = lastupdate;
        }

        public String getIdentifier() {
            return identifier;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public long getLastupdate() {
            return lastupdate;
        }
    }
}