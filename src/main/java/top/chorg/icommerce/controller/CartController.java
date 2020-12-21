package top.chorg.icommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.chorg.icommerce.bean.model.CartItem;
import top.chorg.icommerce.bean.model.ItemModel;
import top.chorg.icommerce.common.GeneralContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

    final GeneralContext generalContext;

    public CartController(GeneralContext generalContext) {
        this.generalContext = generalContext;
    }

    @RequestMapping(value = "")
    public String cart(Model model) {
        model.addAttribute("general", generalContext);
        List<CartItem> cartList = new ArrayList<>();
        // cartList.add(new CartItem(new ItemModel(1,"张三", "233", 400, 100, 0, 10, 0, new Date()), 10));
        // cartList.add(new CartItem(new ItemModel(2,"李四", "233", 500, 200, 0, 20, 0, new Date()), 10));
        // cartList.add(new CartItem(new ItemModel(3,"王五", "233", 600, 300, 0, 30, 0, new Date()), 10));
        model.addAttribute("cartList", cartList);
        return "cart";
    }

}
