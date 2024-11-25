package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.entities.Item;
import bip.vi.Browse.our.Parents.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    //------------ Get randomly --------
    @GetMapping("/exploreRandomly")
    public Item exploreRandomly() {
        return this.itemService.findRandomly();
    }
}
