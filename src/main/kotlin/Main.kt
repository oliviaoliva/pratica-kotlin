package br.ifpb.pdm

fun main() {
    val repositorioAnimal = RepositorioAnimal()
    var opcao = -1
    var nome=""
    var idade= 0

    var color=Color.BLACK;

    while (opcao != 0) {
        menu()
        print("Digite a opção: ")
        opcao = readlnOrNull()?.toInt() ?: -1

        if(opcao in 1..4){
            println("Digite o nome:")
            nome = readlnOrNull().toString().uppercase()

            println("Digite a idade:")
            idade = readlnOrNull()?.toInt() ?: 0

            println("Digite a cor: ")
            try{

                val colorEntry = readlnOrNull().toString().uppercase();
                color = Color.valueOf(colorEntry);
            }
            catch(e:IllegalArgumentException){
              //Caso não encontre a cor digitada
              println("Cor inserida é inválida");
              continue;
            }
        }

        when (opcao) {

            1 -> {
                val cachorro = Cachorro(idade , color)
                cachorro.nome = nome
                repositorioAnimal.adicionar(cachorro)
            }
            2 -> {
                val gato = Gato(idade, color)
                gato.nome = nome
                repositorioAnimal.adicionar(gato)
            }
            3 -> {
                val passaro = Passaro(idade, color)
                passaro.nome = nome
                repositorioAnimal.adicionar(passaro)
            }
            4 -> {
                val humano = Homem(idade, color)
                humano.nome = nome
                repositorioAnimal.adicionar(humano)
            }
            5 -> {
                repositorioAnimal.listar()
            }
            6 -> {
                repositorioAnimal.animais.forEach(Animal::emitirSom)
                repositorioAnimal.animais.forEach { it.emitirSom()}
            }
            7 -> {
                println("Informe o nome do animal para removê-lo:")
                repositorioAnimal.remover(readln().uppercase())
            }
            8 -> {
                println("Digite a cor para listar os animais:")

                val cor = readlnOrNull()

                if (cor != null && Color.entries.any { it.name == cor.uppercase()}) {
                    repositorioAnimal.listarPorCor(Color.valueOf(cor.uppercase()))
                } else {
                    println("Cor inválida.")
                }
            }
            9 -> {
                println("Digite a idade para listar os animais:")
                idade = readlnOrNull()!!.toInt()
                repositorioAnimal.listarPorIdade(idade)
            }
            10 -> {
                println("Digite o nome do animal: ")
                nome = readlnOrNull()?.uppercase() ?: ""
                val animalEncontrado = repositorioAnimal.pesquisarAnimalPorNome(nome);
                println(animalEncontrado ?: "Não foi encontrado nenhum $nome")
            }
        }

    }
}
fun menu() {
    println("1 - Criar Cachorro")
    println("2 - Criar Gato")
    println("3 - Criar Pássaro")
    println("4 - Criar Humano")
    println("5 - Listar Animais")
    println("6 - Emitir som")
    println("7 - Remover um Animal")
    println("8 - Listar Animal por cor")
    println("9 - Listar Animal por idade")
    println("10 - pesquisar Animal por nome")
    println("0 - Sair do programa")

}

enum class Color(){
    RED,
    BLUE,
    BLACK,
    BROWN,
    WHITE
}

class RepositorioAnimal {
    val animais: MutableList<Animal> = mutableListOf()

    fun adicionar(animal: Animal) {
        animais.add(animal)
    }

    fun listar() {
        animais.forEach { println(it) }
    }

    fun remover(nome:String){
        val index = this.getIndexByNome(nome)
        this.animais.removeAt(index)
    }

    private fun getIndexByNome(nome: String): Int {
        val index = this.animais.indexOfFirst { it.nome.equals(nome, ignoreCase = true) }
        if (index == -1) {
            throw IllegalArgumentException("Animal com nome: $nome não encontrado.")
        }
        return index
    }

    fun pesquisarAnimalPorNome(nome: String): Animal? {
        val index = this.animais.indexOfFirst { it.nome.equals(nome, ignoreCase = true) }

        val animal = if (index != -1) this.animais[index] else null;

        return animal;
    }


    fun listarPorCor(cor: Color) {
        val animaisFiltrados = animais.filter { it.color == cor }
        if (animaisFiltrados.isEmpty()) {
            println("Nenhum animal encontrado com a cor $cor")
        } else {
            animaisFiltrados.forEach { println("Nome: ${it.nome}, Idade: ${it.idade}, Cor: ${it.color}") }
        }
    }

    fun listarPorIdade(idade: Int) {
        val animaisFiltrados = animais.filter { it.idade == idade }
        if (animaisFiltrados.isEmpty()) {
            println("Nenhum animal encontrado com a idade $idade.")
        } else {
            animaisFiltrados.forEach { println("Nome: ${it.nome}, Idade: ${it.idade}, Cor: ${it.color}") }
        }
    }
}

 abstract class Animal(var idade: Int, val color: Color) {
    open var nome: String = ""
        get() = "Animal: $field"
        set(valor) {
            field = valor
        }

    abstract fun emitirSom()

    open fun idadeEmAnosHumanos():Int{
        return this.idade * 7
    }

     override fun toString(): String {
         return "nome: $nome idade: $idade cor: $color"

     }
}

class Cachorro(idade: Int, color:Color) : Animal(idade, color) {
    override var nome: String = ""
        get() = field
        set(valor) {
            field = valor
        }
    override fun emitirSom() {
        println("Au au")
    }
}
class Gato(idade: Int,  color:Color) : Animal(idade, color) {
    override fun emitirSom() {
        println("Miau")
    }
}

class Passaro(idade: Int, color:Color) : Animal(idade, color) {
    override fun emitirSom() {
        println("Piu piu")
    }
}

class Homem(idade:Int,color:Color):Animal(idade,color){

    override fun emitirSom() {
        println("Olá, como vai? Me chamo: $nome")
    }

    override fun idadeEmAnosHumanos(): Int {
        return this.idade
    }
}

