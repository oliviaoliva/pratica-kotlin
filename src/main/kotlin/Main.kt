package br.ifpb.pdm

import java.awt.MultipleGradientPaint.ColorSpaceType

fun main() {
    val repositorioAnimal = RepositorioAnimal()
    var opcao = 0
    var nome=""
    var idade= 0
    while (opcao != 9) {
        menu()
        print("Digite a opção: ")
        opcao = readlnOrNull()?.toInt() ?: 0

        if(opcao >= 1 && opcao <= 3){
            println("Digite o nome do animal:")
            nome = readlnOrNull().toString()
            println("Digite a idade do animal:")
            idade = readlnOrNull()?.toInt() ?: 0
        }

        when (opcao) {

            1 -> {
                val cachorro = Cachorro(idade , Color.RED)
                cachorro.nome = nome
                repositorioAnimal.adicionar(cachorro)
            }
            2 -> {
                val gato = Gato(idade, Color.BLUE)
                gato.nome = nome
                repositorioAnimal.adicionar(gato)
            }
            3 -> {
                val passaro = Passaro(idade, Color.BLACK)
                passaro.nome = nome
                repositorioAnimal.adicionar(passaro)
            }
            4 -> {
                repositorioAnimal.listar()
            }
            5 -> {
                repositorioAnimal.animais.forEach(Animal::emitirSom)
                repositorioAnimal.animais.forEach { it.emitirSom()}
            }
            6 -> {
                println("Informe o nome do animal para removê-lo:")
                repositorioAnimal.remover(readln())
            }
            7 -> {
                println("Digite a cor para listar os animais:")
                val cor = readlnOrNull()
                if (cor != null && Color.values().any { it.name == cor.uppercase()}) {
                    repositorioAnimal.listarPorCor(Color.valueOf(cor.uppercase()))
                } else {
                    println("Cor inválida.")
                }
            }
            8 -> {
                println("Digite a idade para listar os animais:")
                val idade = readlnOrNull()?.toInt()
                if (idade != null) {
                    repositorioAnimal.listarPorIdade(idade)
                } else {
                    println("Idade inválida.")
                }
            }
        }

    }
}
enum class Color(){
    RED,
    BLUE,
    BLACK,
    BROWN,
    WHITE
}

open abstract class Animal(var idade: Int, val color: Color) {
    open var nome: String = ""
        get() = "Animal: $field"
        set(valor) {
            field = valor
        }

    open abstract fun emitirSom()

    open fun idadesEmAnosHumanos():Int{
        return this.idade * 7
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

fun menu() {
    println("1 - Criar Cachorro")
    println("2 - Criar Gato")
    println("3 - Criar Pássaro")
    println("4 - Listar Animais")
    println("5 - Emitir som")
    println("6 - Remover um Animal")
    println("7 - Listar Animal por cor")
    println("8 - Listar Animal por idade")
    println("9 - Sair do programa")

}

class RepositorioAnimal {
    val animais: MutableList<Animal> = mutableListOf()

    fun adicionar(animal: Animal) {
        animais.add(animal)
    }

    fun listar() {
        animais.forEach { println("nome: ${it.nome} idade: ${it.idade} cor: ${it.color}") }
    }

    fun remover(nome:String){
        val index = this.pesquisarPorNome(nome)
        this.animais.removeAt(index)
    }

    fun pesquisarPorNome(nome: String): Int {
        val index = this.animais.indexOfFirst { it.nome.equals(nome, ignoreCase = true) }
        if (index == -1) {
            throw IllegalArgumentException("Animal com nome: $nome não encontrado.")
        }
        return index
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