# Activitat 2

## Importació del projecte

Per carregar el projecte al vscode, primer executar la següent comanda dins del directori arrel del projecte:

```bash
scala-cli setup-ide .
```

Una vegada executada, ja podrem obrir el projecte al vscode fent, per exemple:

```bash
code .
```

## Consells de realització

L'ordre suggerit de resolució és el següent:

  1. `BinTree`: different `traverses` related to binary trees.

  2. `SumAction`: create a state action to accumulate values and use it to sum the elements of a list and a tree (uses result of 1). The most important part is understanding how it works.

  3. `Factorial`: use a similar idea of 2, to implement factorial.

  4. `Fibonacci`: using the same ideas of the factorial we implement in class in a worksheet (factorial_worksheet), implement a referentially transparent version of the imperative code of the fibonacci.

  5. `LazyHondt`: implement a lazy version of the Hondt problem, which only computes the "necessary" quaotients.

Com hem fet a classe, el procediment a seguir pot ser:

* solució evident/intuïtiva (possiblement via pattern-matching i/o recursivitat explícita)
* solució usant altres combinadors
* anàlisi de la/les solució/ns anteriors en termes d'eficiència, stack-safeness, etc.
* proposta d'altres solucions

Es valorarà el fet d'haver explorat diferents solucions al mateix problema.

Per cada fitxer he creat una petita classe de proves per a que podeu afegir tests que comprovin el funcionament del vostre codi.

També podeu usar worksheets per a fer-ho interactivament (teniu un exemple de worksheet al directori `worksheets`)

Si voleu saber una mica més les possibilitats de la biblioteca de tests, podeu consultar la seva documentació a [munit homepage](https://scalameta.org/munit/)

## Què heu d'entregar

* Projecte amb la resolució dels exercicis, amb els tests o worksheets que heu afegit.

  * Per poder tenir vàries solucions del mateix problema podeu donar-lis noms numerats

* Petit informe, en PDF, explicant el procés de resolució de cada problema
