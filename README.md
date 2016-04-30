# introsde-adapter

Root available at https://infinite-garden-2438.herokuapp.com/sdelab

## Resources:

### QUOTE

* https://infinite-garden-2438.herokuapp.com/sdelab/quote 

Returns an TEXT_XML containing the quote. 

### FOOD

The results are in XML and JSON:

<foods>
    <food>
        <calories>2176.272</calories>
        <description>8 small whiting fish or smelt4 cups vegetable oil</description>
        <idFood>0</idFood>
        <name>Deep Fried Fish Bones</name>
        <type>fish</type>
    </food>
    <food>
        <calories>203.02224999999999</calories>
        <description>Ajwain fish 200 gramsChili powder tspPepper powder 1/4 tspTurmeric powder tspSalt tspCurry leaves few</description>
        <idFood>1</idFood>
        <name>Ajwain fish Fry ( South Indian )</name>
        <type>fish</type>
    </food>
</foods>

[
    {
        "idFood": 0,
        "description": "8 small whiting fish or smelt4 cups vegetable oil",
        "type": "fish",
        "calories": 2176.272,
        "name": "Deep Fried Fish Bones"
    },
    {
        "idFood": 1,
        "description": "Ajwain fish 200 gramsChili powder tspPepper powder 1/4 tspTurmeric powder  tspSalt  tspCurry leaves few",
        "type": "fish",
        "calories": 203.02224999999999,
        "name": "Ajwain fish Fry ( South Indian )"
    }
    ]

#### GET

* https://infinite-garden-2438.herokuapp.com/sdelab/food/{type}

Retrieves all food that contains {type}

* https://infinite-garden-2438.herokuapp.com/sdelab/food/{type}/{caloriesBound}

Retrieves all food that contains {type} but filtered for calories
