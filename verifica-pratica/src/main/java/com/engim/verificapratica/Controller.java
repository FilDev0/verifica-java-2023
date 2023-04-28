package com.engim.verificapratica;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    private static Sorteggio sorteggio = new Sorteggio();

    // ES 1.
    @GetMapping(value = "/add")
    public static void aggiungiSquadra(@RequestParam(value="nome") String nome, @RequestParam(value="nazione") String nazione){
        sorteggio.aggiungi(nome,nazione);
    }

    // ES 2.

    @GetMapping(value = "/listaSquadre")
    public static List<Squadra> listaSquadre(@RequestParam(value="nazione") String nazione){
        List<Squadra> squadrePerNazione = new ArrayList<>();
        for (Squadra s : sorteggio.getSquadre()) {
            System.out.println(s);
            if (s.getNazione().equals(nazione)){
                squadrePerNazione.add(s);
            }
        }
        return squadrePerNazione;
    }


    // ES 3.
    @GetMapping(value = "/sorteggia")
    public static List<Squadra> sorteggia(){
        List<Squadra> squadreSorteggiate = new ArrayList<>();
        Squadra primaSquadra = sorteggio.next();
        squadreSorteggiate.add(primaSquadra);
        boolean condizione = true;


        while (condizione){
            Squadra s = sorteggio.next();
            if (!(s.getNazione().equals(primaSquadra.getNazione()))){
                squadreSorteggiate.add(s);
                condizione=false;
            }
        }


        return squadreSorteggiate;
    }



    // ES 4.

    @GetMapping(value = "getPartite")
    public static List<Partita> sorteggiaPartite(){
        List<Partita> partite = new ArrayList<>();

        if (sorteggio.getSquadre().size() % 2 ==0){
            for (int i=0; i < sorteggio.getSquadre().size() ; i=i+2) {
                partite.add(new Partita(sorteggio.next(), sorteggio.next()));
            }

        }else {
            throw new RuntimeException();
        }
        return partite;
    }


    /*
    * ES 1: get ("/add?nome=n&nazione=m") che aggiunge al sorteggio una squadra con nome n e nazione m (utilizzare
    * la classe Sorteggio) - 15 p
    *
    * ES 2: get ("/listaSquadre?nazione=n") che restituisce la lista delle squadre di nazione n - 20 p
    *
    * ES 3: get ("/sorteggia") che restituisce 2 squadre di nazioni diverse (utilizzare la classe Sorteggio, il metodo
    * next. Consiglio: dopo aver sorteggiato la prima, continuare a sorteggiare finché la seconda
    * non è di una nazione diversa) - 20 p
    *
    * ES 4: implementare il sorteggio delle squadre di una fase finale di un torneo a eliminazione diretta.
    * Creare il metodo sorteggiaPartite che:
    * - controlla se il numero di squadre aggiunte è una potenza di 2. Se non lo è lancia una RuntimeException.
    * - Finché ci sono squadre non sorteggiate: sorteggia 2 squadre e le inserisce in un oggetto della classe Partita (da creare)
    * - restituisce la lista di Partite.
    * creare get ("/getPartite") che restituisce la lista appena creata - 30 p
    * */

}
