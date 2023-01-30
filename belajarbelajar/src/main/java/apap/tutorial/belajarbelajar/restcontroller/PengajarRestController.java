package apap.tutorial.belajarbelajar.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import apap.tutorial.belajarbelajar.model.PengajarModel;
import apap.tutorial.belajarbelajar.service.PengajarRestService;

@RestController
@RequestMapping("/api/v1")
public class PengajarRestController {
    @Autowired
    private PengajarRestService pengajarRestService;

    @PostMapping(value = "/pengajar/add")
    private PengajarModel createPengajar(@Valid @RequestBody PengajarModel pengajar, BindingResult bindingResult)
    {
        if(bindingResult.hasFieldErrors()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field.");
        }
        else {
            return pengajarRestService.createPengajar(pengajar);
        }
    }

    @GetMapping(value = "/pengajar/{noPengajar}")
    private PengajarModel retrievePengajar(@PathVariable("noPengajar") Long noPengajar){
        try {
            return pengajarRestService.getPengajarByNoPengajar(noPengajar);
        }
        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pengajar with code" + noPengajar + " not found");
        }
    }

    @GetMapping(value = "/pengajar/jenis-kelamin/{noPengajar}")
    private PengajarModel GenderPengajar(@PathVariable("noPengajar")Long noPengajar){
        try {
            return pengajarRestService.getPengajarGenderByNoPengajar(noPengajar);
        }
        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pengajar with code" + noPengajar + " not found");
        }
    }


    @DeleteMapping(value = "/pengajar/{noPengajar}")
    private ResponseEntity deletePengajar(@PathVariable("noPengajar") Long noPengajar){
        try {
            pengajarRestService.deletePengajar(noPengajar);
            return ResponseEntity.ok("Pengajar has been deleted");

        }
        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pengajar with code" + noPengajar +" not found");
        }
        catch (UnsupportedOperationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hayo napa");
        }
    }

    @PutMapping(value = "/pengajar/{noPengajar}")
    private PengajarModel updatePengajar(@PathVariable("noPengajar") Long noPengajar, @RequestBody PengajarModel pengajar){
        try {
            return pengajarRestService.updatePengajar(noPengajar, pengajar);
        }
        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pengajar with code" + noPengajar + " not found");
        }
    }

    @GetMapping(value = "/list-pengajar")
    private List<PengajarModel> retrieveListPengajar(){
        return pengajarRestService.retrieveListPengajar();
    }

}
