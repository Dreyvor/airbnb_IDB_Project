# airbnb_IDB_Project

## TODO:
- [x] Ajouter les *\*_calendar.csv* dans le schema ER
- [x] Ajouter les  *\*_reviews.csv* dans le schema ER
- [ ] Est-ce qu'on laisse *interaction* (11) dans le *house_details* ?
- [x] *Host_verification* (24) => pick_list ?
- [x] *Property_type* (31) => pick_list ?
- [x] *Room_type* (32) => pick_list ?
- [x] *Bed_type* (37) => pick_list ?
- [x] *City* (26) => pick_list ?
- [ ] *Host_verification* (24) => c'est une string => scala => découpage pour sortir le set => pick_list
- [ ] *Amenities* (38) => c'est une string => scala => découpage pour sortir le set => pick_list
- [x] *Cancellation_policies* (57) => pick_list ?
- [x] *Host_response_time* (19) => pick_list ?
- [ ] *Host_response_rate* (20) => scala => c'est genre "92%" faudra juste garder le "92" en tant que int
---
## Informations:
* On a supprimé host_Id, host_name, reviewer_id et reviewer_name qui se trouveront dans la table User en tant que User_name, user_id
