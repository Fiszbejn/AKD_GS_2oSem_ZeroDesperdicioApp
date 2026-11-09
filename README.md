# Circuito Zero Desperdício 🌱

Este projeto é um aplicativo Android nativo desenvolvido com **Kotlin** e **Jetpack Compose** para a Global Solution da FIAP. O aplicativo atua como uma plataforma relâmpago de redistribuição de excedentes alimentares, conectando comércios de bairro a instituições de caridade e líderes comunitários.

Neste MVP, o escopo foca na experiência do **consumidor/ONG**, permitindo a visualização de alimentos disponíveis na região, detalhamento das ofertas e reserva imediata para retirada.

---

## 🌍 Tema da Global Solution
**Circuito Zero Desperdício (Foco: Fome e Logística Urbana)**

O problema resolvido é o descarte de alimentos em perfeitas condições por restaurantes e padarias, enquanto famílias enfrentam insegurança alimentar. A solução quebra o entrave logístico da doação ao focar na coleta imediata a pé ou em trajetos curtíssimos no próprio bairro.

---

## 📱 Fluxo de Telas
O aplicativo possui 6 telas, gerenciadas através do `Navigation Compose`:
1. **Splash Screen:** Exibe o logo e aguarda 2 segundos. Verifica via `SharedPreferences` se é o primeiro acesso.
2. **Onboarding (Introdução):** Apresenta a proposta de valor do app. Salva o status de visualização para não ser exibida novamente.
3. **Home (Feed de Ofertas):** Consome a API REST e lista os excedentes disponíveis utilizando `LazyColumn` e `Card`s personalizados. Possui tratamento de estado (Loading, Success, Error).
4. **Detalhes da Oferta:** Exibe informações detalhadas de um item selecionado, consumindo a rota de ID específico da API. Permite ao usuário "Reservar" o item.
5. **Meus Resgates:** Filtra e exibe o histórico de alimentos reservados pelo usuário, integrando os dados da API com os IDs salvos localmente.
6. **Perfil:** Permite editar o nome da Instituição (persistido localmente) e realizar o logout (limpeza do cache e retorno ao Splash).

---

## 🖼️ Prints das Telas
*(Substitua os links abaixo pelos prints reais do seu emulador/celular. Você pode arrastar as imagens direto para a caixa de edição do GitHub para gerar os links)*

<p align="center">
  <img width="200" alt="splash" src="https://github.com/user-attachments/assets/5af3276c-e274-4582-a94e-c2f11a2590d7" />
  <img width="200" alt="intro" src="https://github.com/user-attachments/assets/abc6475e-2a77-4e83-8ec5-ebdb2203e2a7" />
  <img width="200" alt="home" src="https://github.com/user-attachments/assets/74eeec37-0821-4ef8-9d03-61406e089e3f" />
</p>
<p align="center">
  <img src="URL_DA_IMAGEM_DETALHES" width="200"/>
  <img width="200" alt="detalhes" src="https://github.com/user-attachments/assets/61d62dbc-97d4-43a7-81ce-b79da10bdac6" />
  <img width="200" alt="resgates" src="https://github.com/user-attachments/assets/0f8c168e-65f9-4841-b7f4-51acaf6c5a5a" />
  <img width="200" alt="perfil" src="https://github.com/user-attachments/assets/41238050-8719-4f5b-b3d0-738eedbf6131" />
</p>

---

## 🔌 API Utilizada
Foi construída uma API RESTful *mockada* utilizando o serviço **MockAPI.io** para simular o banco de dados dos comércios locais.

* **Base URL:** `https://6aefef92b9925b27ce17123f.mockapi.io/ZeroDesperdicioApp/api/`
* **Endpoints Consumidos:**
  * `GET /ofertas` - Retorna a lista completa de excedentes.
  * `GET /ofertas/{id}` - Retorna os detalhes de um alimento específico.

A comunicação de rede foi implementada utilizando **Retrofit2** e **Gson** para desserialização, com chamadas assíncronas utilizando **Kotlin Coroutines**. As imagens da web são renderizadas assincronamente através da biblioteca **Coil**.

---

## 🏗️ Explicação da Arquitetura
O projeto segue os princípios da **Clean Architecture** e adota o padrão **MVVM (Model-View-ViewModel)** com **Repository Pattern**, garantindo alta coesão e baixo acoplamento:

* **UI/View (`ui/screens`):** Telas construídas 100% em Jetpack Compose. Não possuem regras de negócio, apenas reagem aos estados emitidos pelas ViewModels. Componentes reutilizáveis (como o `OfertaCard`) foram isolados.
* **ViewModel (`ui/screens/home` e `details`):** Orquestram as chamadas de dados e gerenciam o ciclo de vida da tela através do `viewModelScope`. Utilizam `StateFlow` e `Sealed Classes` para gerenciar os 3 estados obrigatórios da interface: `Loading`, `Success` e `Error`.
* **Repository (`data/repository`):** Implementa o *Dependency Inversion Principle* (SOLID) através de uma **Interface** (`OfertaRepository`) e sua implementação concreta (`OfertaRepositoryImpl`). Responsável por abstrair a origem dos dados (API) da ViewModel.
* **Local Data (`data/local`):** Gerenciamento de persistência de dados leves com `SharedPreferences` (Helper isolado) para controle de onboarding, favoritos (Meus Resgates) e dados de perfil.

---

## 👨‍💻 Desenvolvedores
- Davi Fiszbejn - 557716
- Augusto Tomaselli - 555916
- Leonardo da Silva de Oliveira - 557333

---

## 🎥 Vídeo Pitch
*(Cole aqui o link do YouTube/Vimeo com a apresentação e demonstração do app rodando)*

[Assistir ao Vídeo Pitch do Projeto](COLE_O_LINK_AQUI)
