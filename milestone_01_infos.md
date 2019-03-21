# Milestone 1, 25.03.19
### TODO BRAD:
* check relation ternaire

---

### INFOS pour le rapport:
* On a mis *host_name*(16), *host_id*(14), *reviewer_id* et *reviewer_name* dans *user_name* et *user_id* parce qu'on a une relation ISA entre Host, reviewer qui sont des users.
* *host_response_time*(19): charger les données comme étant des string et mettre une string par défaut si les données sont "N/A"
* *host_response_rate*(20): charger les données comme étant des nombres (transformer le  "XX%" en integer "XX") en transformant les "N/A" en null
* *host_verifications*(24): on va créer une picklist en séparant les différentes string donnée dans les \*.csv
* *amenities*: on va créer une picklist en séparant les différentes string donnée dans les \*.csv
* Pour le schema relationnel, on a supprimé l'entité *Reviewers* car elle n'a pas d'attributs et on a fait une Foreign key dans *Review_comments* directement sur la table *User*
* Décrire la transistion ERM -> relationnel pour la relation ternaire
* Pour la description de la transition ERM -> relationnel, il faut décrire les foreign keys (pourquoi on fait de cette façon)
* Les clés primaires suivantes sont autoincrémentées:
	* *amenities_id*
	* *city_id*
	* *host_verifications_id*
	* *neighbourhood_id*
