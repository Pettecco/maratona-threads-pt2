# Projeto de Sistemas Operacionais
## Objetivo: Demonstrar os conceitos de escalonamento e sincronismo de processos

### Apresentação
O projeto simula uma Maratona de Programação. As equipes tentam resolver problemas e enviá-los para um juiz validar. Com a validação, o juiz consegue alterar o placar das equipes.  
Ao final da maratona, o placar é mostrado, contendo os pontos das equipes e a quantidade de problemas solucionados.  
Cada equipe resolverá os problemas de uma certa forma: algumas optando por fazer os mais difíceis primeiro, outras resolvendo na ordem recebida.

### Problemas a serem explorados
Cada equipe é uma *thread*, portanto, todas resolvem problemas ao mesmo tempo.  
Mas o que aconteceria caso duas equipes submetessem simultaneamente para o juiz?  
Naturalmente, haveria risco de conflito, possivelmente gerando uma exceção e encerrando o programa.

**Como evitar esse problema?**  
Além disso, era necessário simular os métodos de resolução das equipes, que representariam os algoritmos de escalonamento de um sistema operacional.  
**Como simular esse comportamento?**

### Solução
De forma simples: usamos `BlockingQueue` e classes que simulam algoritmos de escalonamento.  
A seguir, o funcionamento do projeto é descrito de forma mais técnica, incluindo seu fluxo e suas classes.

---

## Fluxo
Na classe `main`, os problemas são gerados, com IDs sequenciais e outros atributos definidos aleatoriamente.  
Em seguida, as classes `Placar` e `Juiz` são inicializadas, bem como os escalonadores e as equipes (cada uma associada a um escalonador).  
A maratona é iniciada, recebendo as equipes, o juiz, o placar e a duração da competição.

Após isso, as *threads* das equipes e do juiz são iniciadas.  
Cada equipe recebe os problemas e simula seu tempo de resolução. Quando um problema é resolvido, é feita uma submissão ao juiz, que o coloca em uma fila (`BlockingQueue`).  
O juiz retira problemas dessa fila para revisar. Ao revisar, ele atualiza os pontos no placar.

O fluxo continua até o tempo da maratona acabar.  
Ao final, uma "submissão vazia" (conhecida como *poison pill*) é inserida na fila do juiz para indicar que ele deve encerrar sua execução.  
As equipes são interrompidas e param de submeter problemas.  
O juiz continua avaliando os itens da fila até chegar à *poison pill*, encerrando-se em seguida.  
Depois disso, as estatísticas finais são exibidas, e o programa termina.

---

## Classes

### `Problema`
Representa um problema da maratona. Cada um possui: ID, dificuldade, tempo de resolução, pontuação, tempo restante (usado no escalonador RR) e um gerador de números aleatórios.  
Inclui métodos auxiliares para acessar atributos e verificar condições, além de um `toString` para exibição simplificada.

---

### `Equipe` *(implementa Runnable)*
Cada equipe possui nome, escalonador, juiz e uma lista de problemas.  
Implementa a interface `Runnable`, funcionando como uma *thread*.  
O método `run` executa enquanto houver problemas e a thread não for interrompida.  
O problema é escolhido pelo escalonador da equipe e resolvido (simulando tempo com `Thread.sleep`).  
Ao final, a submissão é enviada ao juiz.

> Para Round Robin, o tempo de resolução é gerenciado de forma diferente, dentro do próprio escalonador.

---

### `Submissao`
Simples estrutura que conecta uma equipe a um problema resolvido. Serve para enviar essa informação ao juiz.

---

### `EstatisticasEquipe`
Armazena e exibe estatísticas individuais de uma equipe: pontos, problemas resolvidos e tempo total de resolução.

---

### `Escalonador` *(interface)*
Define um contrato comum para os algoritmos de escalonamento.  
Possui um método `selecionarProblema(List<Problema>)`, que deve ser implementado por todas as subclasses.

---

### `EscalonadorFCFS` *(First Come, First Served)*
Seleciona o primeiro problema disponível. Se não houver, retorna `null`.

---

### `EscalonadorPSD` *(Priority Scheduling por Dificuldade)*
Seleciona o problema de maior dificuldade. Se não houver, retorna `null`.

---

### `EscalonadorPSP` *(Priority Scheduling por Pontuação)*
Seleciona o problema com maior pontuação. Se não houver, retorna `null`.

---

### `EscalonadorRR` *(Round Robin)*
Seleciona problemas baseando-se em *quantum* (tempo fixo de execução).
- O quantum é de 2 segundos.
- Os problemas são armazenados em uma fila circular.
- Ao ser selecionado, calcula-se se ele será resolvido dentro do quantum ou se precisará voltar para a fila.
- Caso resolvido, é retornado para submissão. Se não, é reencaminhado ao fim da fila.

---

### `Juiz` *(implementa Runnable)*
Resolve o problema de concorrência de acessos simultâneos.  
Usa uma `BlockingQueue` para receber submissões, garantindo segurança entre múltiplas threads.  
Após receber uma submissão, simula a avaliação (com `Thread.sleep`) e atualiza o placar.  
Quando a maratona termina, o método `encerrar()` define a flag de encerramento e adiciona uma *poison pill* à fila.

---

### `Placar`
Armazena as estatísticas das equipes em um `HashMap`, usando o nome como chave.
- O método `registrar` adiciona ou atualiza os dados.
- O método `imprimir` exibe as pontuações ao final da maratona.

---

### `Maratona`
Recebe a lista de equipes, o juiz, o placar e a duração da maratona.  
Inicia todas as *threads* envolvidas e controla sua execução.  
Após o tempo expirar, interrompe as equipes e finaliza o juiz, que por sua vez processa até a *poison pill*.  
Por fim, o placar é exibido.
