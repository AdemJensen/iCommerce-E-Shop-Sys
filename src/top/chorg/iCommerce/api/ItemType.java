package top.chorg.iCommerce.api;

public class ItemType {
    public int id;
    public String name;
    public String typeComment;
    public int cover;

    public ItemType(int id, String name, String typeComment, int cover) {
        this.id = id;
        this.name = name;
        this.typeComment = typeComment;
        this.cover = cover;
    }

    public static String[] getNameWrap(ItemType[] source) {
        if (source == null) return new String[0];
        String[] res = new String[source.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = source[i].name;
        }
        return res;
    }
    public static int[] getIdWrap(ItemType[] source) {
        if (source == null) return new int[0];
        int[] res = new int[source.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = source[i].id;
        }
        return res;
    }
}
