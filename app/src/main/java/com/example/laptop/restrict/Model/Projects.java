package com.example.laptop.restrict.Model;


import java.util.List;

public class Projects {


    private Integer id;

    private String identifier;

    private String title;

    private String archived;

    private String created_at;

    private String updated_at;

    private List<Sections> sectionsList;


    public Projects() {
    }

    public Projects(int id, String identifier, String title, String archived, String created_at, String updated_at, List<Sections> sectionsList) {
        this.id = id;
        this.identifier = identifier;
        this.title = title;
        this.archived = archived;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.sectionsList = sectionsList;
    }


    public class Sections
    {
        private int id;
        private String project_id;
        private String title;
        private String identifier;
        private String created_at,updated_at;
        private List<Drawings> drawingsList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public List<Drawings> getDrawingsList() {
            return drawingsList;
        }

        public void setDrawingsList(List<Drawings> drawingsList) {
            this.drawingsList = drawingsList;
        }
    }

    public class Drawings
    {
        private int id;
        private String section_id;
        private String title;
        private String identifier;
        private String created_at,updated_at;
        private List<Versions> versionsList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSection_id() {
            return section_id;
        }

        public void setSection_id(String section_id) {
            this.section_id = section_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public List<Versions> getVersionsList() {
            return versionsList;
        }

        public void setVersionsList(List<Versions> versionsList) {
            this.versionsList = versionsList;
        }
    }
    public class Versions
    {
        private int id;
        private String drawing_id;
        private String parent_id;
        private String user_id;
        private String type;
        private String label;
        private String image_file,pdf_file;
        private String issued_for;
        private String created_at,updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDrawing_id() {
            return drawing_id;
        }

        public void setDrawing_id(String drawing_id) {
            this.drawing_id = drawing_id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getImage_file() {
            return image_file;
        }

        public void setImage_file(String image_file) {
            this.image_file = image_file;
        }

        public String getPdf_file() {
            return pdf_file;
        }

        public void setPdf_file(String pdf_file) {
            this.pdf_file = pdf_file;
        }

        public String getIssued_for() {
            return issued_for;
        }

        public void setIssued_for(String issued_for) {
            this.issued_for = issued_for;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }


}
