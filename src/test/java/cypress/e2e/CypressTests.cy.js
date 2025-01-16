describe('template spec', () => {
  it('Site should exist', () => {
    cy.visit('http://localhost:8080/')
    cy.get('[data-testid="title2"]').should("exist")

  })

  it('Should be able to change task title', () => {
    cy.visit('http://localhost:8080/')
    cy.visit('http://localhost:8080/details/2')
    cy.get('[data-testid="titleBuy milk"]').should("exist").clear().type("Sell milk")
    cy.get('[data-testid="submitChanges"]').click()
    cy.get('[data-testid="title2"]').should("exist").should('contain', 'Sell');

  })

  it('Should be able to change task description', () => {
    cy.visit('http://localhost:8080/')
    cy.visit('http://localhost:8080/details/2')
    cy.get('[data-testid="decriptionSell milk"]').should("exist").clear().type("Sell the milk you just bought")
    cy.get('[data-testid="submitChanges"]').click()
    cy.get('[data-testid="description2"]').should("exist").should('contain', 'Sell');

  })

  it('Should be able to add new Task', () => {
    cy.visit('http://localhost:8080/new')
    cy.get('[data-testid="titleInput"]').should("exist").clear().type("Do the testing")
    cy.get('[data-testid="descriptionInput"]').should("exist").clear().type("Finish the project")
    cy.get('[data-testid="submitChanges"]').click()
    cy.get('[data-testid="description5"]').should("exist").should('contain', 'Finish');
    cy.get('[data-testid="title5"]').should("exist").should('contain', 'testing');

  })

  it('Should be able to delete existing Task', () => {
    cy.visit('http://localhost:8080')
    cy.get('[data-testid="delete4"]').should("exist").click()
    cy.get('[data-testid="title4"]').should("not.exist")
  })

  it('Should not be able to add empty task', () => {
    cy.visit('http://localhost:8080/new')
    cy.get('[data-testid="titleInput"]').should("exist").clear()
    cy.get('[data-testid="descriptionInput"]').should("exist").clear()
    cy.get('[data-testid="submitChanges"]').click()
    cy.url().should('eq', 'http://localhost:8080/new');
  })

  it('Should be able to add task wihout description', () => {
    cy.visit('http://localhost:8080/new')
    cy.get('[data-testid="titleInput"]').should("exist").clear().type("Emptiness")
    cy.get('[data-testid="descriptionInput"]').should("exist").clear()
    cy.get('[data-testid="submitChanges"]').click()
    cy.get('[data-testid="title6"]').should("exist").should('contain', 'Emptiness');
    cy.get('[data-testid="description6"]').should("exist").should('be.empty');
  })

  it('Should not be able to change task title to empty', () => {
    cy.visit('http://localhost:8080/details/2')
    cy.get('[data-testid="titleSell milk"]').should("exist").clear()
    cy.get('[data-testid="submitChanges"]').click()
    cy.url().should('eq', 'http://localhost:8080/details/2');

  })

  it('Should not be able to change task title to longer than 50 characters', () => {
    cy.visit('http://localhost:8080/details/2')
    cy.get('[data-testid="titleSell milk"]').should("exist").clear().type("Moim zdaniem to nie ma tak, że dobrze albo że nie dobrze. Gdybym miał powiedzieć, co cenię w życiu najbardziej, powiedziałbym, że ludzi. Ekhm... Ludzi, którzy podali mi pomocną dłoń, kiedy sobie nie radziłem, kiedy byłem sam. I co ciekawe, to właśnie przypadkowe spotkania wpływają na nasze życie. Chodzi o to, że kiedy wyznaje się pewne wartości, nawet pozornie uniwersalne, bywa, że nie znajduje się zrozumienia, które by tak rzec, które pomaga się nam rozwijać. Ja miałem szczęście, by tak rzec, ponieważ je znalazłem. I dziękuję życiu. Dziękuję mu, życie to śpiew, życie to taniec, życie to miłość. Wielu ludzi pyta mnie o to samo, ale jak ty to robisz?, skąd czerpiesz tę radość? A ja odpowiadam, że to proste, to umiłowanie życia, to właśnie ono sprawia, że dzisiaj na przykład buduję maszyny, a jutro... kto wie, dlaczego by nie, oddam się pracy społecznej i będę ot, choćby sadzić... znaczy... marchew.")
    cy.get('[data-testid="submitChanges"]').click()
    cy.url().should('eq', 'http://localhost:8080/details');

  })

  it('Should not be able to change task description to longer than 50 characters', () => {
    cy.visit('http://localhost:8080/details/2')
    cy.get('[data-testid="decriptionSell milk"]').should("exist").clear().type("Strasznie dużo wczoraj wypiłem. Paliłem papierosy do piątej rano. Film mi się urwał jak leżałem w rurze. Teraz mnie krzyż boli. Trochę się przespałem, ale musiałem wstać rano bo mam obowiązki. Mam dziecko. Niektórzy mówią, że nie można pić jak się ma dzieci, ale to nieprawda. Można, tylko trzeba wstawać rano. Na tym polega odpowiedzialność.")
    cy.get('[data-testid="submitChanges"]').click()
    cy.url().should('eq', 'http://localhost:8080/details');

  })
})