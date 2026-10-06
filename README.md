# API de gestion de tâches

Petite API REST de gestion de tâches, développée avec Java 17, Spring Boot 4, Spring Data JPA et une base H2 en mémoire. Le projet ne comporte ni frontend, ni authentification, ni déploiement.

## Prérequis

JDK 17 ou supérieur et Maven 3.9 ou supérieur. Le projet a été développé et testé avec le JDK 21.

## Lancement et tests

    mvn spring-boot:run     # démarre l'API sur http://localhost:8080
    mvn test                # exécute les tests automatisés

Les données sont conservées en mémoire et sont perdues à l'arrêt de l'application. La console H2 n'est pas nécessaire pour utiliser l'API.

## Règles métier

Une tâche comporte un identifiant, un titre obligatoire (de 1 à 120 caractères après suppression des espaces en début et en fin de chaîne), une description facultative (200 caractères au maximum) et un statut parmi TODO, IN_PROGRESS et DONE. Le statut initial est TODO.

Seules les transitions TODO vers IN_PROGRESS, puis IN_PROGRESS vers DONE, sont autorisées. Toute autre transition, y compris vers le statut courant, est refusée. Les statuts sont attendus en majuscules exactes.

## Endpoints

POST /tasks crée une tâche. La réponse est 201 avec un en-tête Location, ou 400 si le titre ou la description est invalide ou si le JSON est illisible.

GET /tasks retourne la liste des tâches triée par identifiant, avec un filtre optionnel ?status=TODO. La réponse est 200, ou 400 si le statut est inconnu.

PATCH /tasks/{id}/status modifie le statut d'une tâche. La réponse est 200, ou 400 si le statut est absent ou inconnu, 404 si l'identifiant n'existe pas, 409 si la transition est interdite. Lorsqu'un statut inconnu et un identifiant absent sont combinés, la validation du statut est effectuée en premier et la réponse est 400.

Les erreurs sont retournées au format JSON suivant, avec des messages rédigés en anglais :

    {"status": 409, "error": "Conflict", "message": "Invalid transition: TODO -> DONE. Allowed order: TODO -> IN_PROGRESS -> DONE."}

## Exemples de requêtes

    curl -i -X POST localhost:8080/tasks -H 'Content-Type: application/json' \
      -d '{"title":"Write the README","description":"Fictional data"}'

    curl -i localhost:8080/tasks
    curl -i 'localhost:8080/tasks?status=TODO'

    curl -i -X PATCH localhost:8080/tasks/1/status -H 'Content-Type: application/json' \
      -d '{"status":"IN_PROGRESS"}'

## Structure

Le code est organisé par couches : controller (couche HTTP), service (validation et règles métier), repository (accès aux données), entity (entité JPA et énumération des statuts, qui porte la règle de transition), dto (objets d'échange) et exception (exceptions métier et traduction en codes HTTP).

## Tests

Trois tests automatisés d'intégration (classe TaskApiTest, MockMvc) couvrent les cas exigés : création valide, titre invalide et transition interdite. Le test de démarrage du contexte généré par Spring Initializr s'y ajoute. Les autres scénarios (404 pour un identifiant absent, 400 pour un statut inconnu, filtre par statut, transitions valides) ont été vérifiés manuellement avec Postman.

## Temps passé

À compléter : environ X heures Y minutes (limite fixée à 2 heures).

## Utilisation de l'IA et de la documentation

À compléter avec précision. Exemple de formulation, à adapter à la réalité : un assistant d'IA (Claude) a été utilisé pour proposer l'architecture et du code que j'ai ensuite relu, adapté et testé ; la documentation officielle de Spring a été consultée pour les points de configuration.

## Limites

L'API ne propose ni pagination, ni suppression, ni modification du titre ou de la description. Aucun contrôle de concurrence n'est mis en place : deux changements de statut simultanés ne sont pas arbitrés. Le schéma est généré par Hibernate au démarrage, sans outil de migration, et la base n'existe qu'en mémoire. La couverture automatisée se limite aux trois cas exigés.