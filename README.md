# Recipe Information System

A Java command-line application for organizing recipes, ingredients, cooking steps, and preparation times. It supports searching and editing recipes, rule-based recommendations, and local ratings and favorites. Plain-text storage lets users reload their changes the next time they run the app.

## Run locally

Install JDK 17 or later and run these commands from the repository root.

```sh
javac -encoding UTF-8 -d build *.java
java -cp build RecipeDatabase
```

Choose **2**, then **1**, and enter **Lemon Rice** to search by name. Choose **6** to return to the main menu, **3** for recommendations, or **7** to exit. The included recipes are fictional examples. The application writes its text files in the current working directory when you save.

## Run the checks

Compile with the command above, then run any of the ten standalone check classes. For example:

```sh
java -cp build TestRecipeDatabase
java -cp build TestLoadRecipes
java -cp build TestSaveRecipes
```

The classes also cover recipe fields, cooking instructions, ingredients, dietary tags, recommendations, and user interactions. Dietary tags are example data.
