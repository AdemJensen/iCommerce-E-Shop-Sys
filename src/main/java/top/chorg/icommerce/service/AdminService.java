package top.chorg.icommerce.service;

import org.springframework.ui.Model;

public interface AdminService {

    /**
     * Display the editor of the index items.
     *
     * @return View template.
     */
    String displayIndexItemList(Model model);

}
