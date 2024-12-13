package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.entities.Item;
import bip.vi.Browse.our.Parents.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{itemId}")
    public Item getItemById(@PathVariable String itemId) {
        return this.itemService.findById(itemId);
    }

    @PatchMapping("/{itemId}/img")
    public String setImg(@PathVariable("itemId") String itemId, @RequestParam("img") MultipartFile img) {
        return this.itemService.setImg(itemId, img);
    }
}
