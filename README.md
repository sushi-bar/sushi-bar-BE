# sushi-bar-BE [![Build Status](https://api.travis-ci.org/egch/sushi-bar-BE.svg?branch=master)](https://travis-ci.org/egch/sushi-bar-BE)
Back End of the sushi bar application. 



# Requirement
Management system of a virtual sushi booth in which each customer can consult the online menu; once registered
can place the order, indicate the table to be served and pay the bill.
Once the bill has been paid, the cook appears with the order to cook and serve to the customer his dinner / lunch.
A room administrator has a dashboard available to manage users, customize and compose the menu with the prices of the courses, supervise the
work of the cook.

# Architecture (draft)
Based on Apache Kafka & spring boot.


# Original Requirement (italian)



progetto: gestione di un sushi bar

consegna: 8 settimane divisi in sprint di 2 settimane ciascuno

descrizione: sistema di gestione di un baracchino del sushi virtuale in 
cui ogni cliente può consultare il menù online; una volta registrato
può effettuare l'ordine, indicare il tavolo a cui esssere servito e pagare il conto.
Una volta pagato il conto al cuoco compare la comanda da cucinare e servire
al cliente la sua cena/pranzo.
Un amministratore del locale ha a disposizione una dashboard per gestire gli utenti,
personalizzare e comporre il menù con i prezzi delle portate, supervisionare il 
lavoro del cuoco.

Piano di consegna.

sprint 1
- registrazione utenti (nominativo, email, password) con conferma via mail
- accesso amministratore da backend
- accesso cuoco da backend
- home page con info del ristorante

sprint 2:
- gestione pietanze e costi da parte dell'amministratore (nome, costo, quantità, descrizione, foto)
- navigazione del menù da parte del cliente e possibilità di compore il carrello con le pietanze di cena/pranzo

sprint 3:
- processo di pagamento fake per utenti registrati e invio della comanda al cuoco
- gestione delle comande da parte del cuoco: presa in carico e completamento (fake)
- avviso al cliente (tramite mail) che la sua cena è pronta

sprint 4:
- gestione utenti da parte dell'aministratore
- cambio password per password dimenticata

