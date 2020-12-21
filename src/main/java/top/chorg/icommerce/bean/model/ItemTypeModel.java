package top.chorg.icommerce.bean.model;

public class ItemTypeModel {
    private final int t_id;
    private final String t_name;
    private final String t_description;
    private final int t_father;

    public ItemTypeModel(int t_id, String t_name, String t_description, int t_father) {
        this.t_id = t_id;
        this.t_name = t_name;
        this.t_description = t_description;
        this.t_father = t_father;
    }

}
