package com.ilfalsodemetrio.utils.ui

import com.ilfalsodemetrio.utils.CodiceFiscaleChecker
import com.ilfalsodemetrio.utils.entity.Localita
import com.ilfalsodemetrio.utils.entity.Persona
import groovy.swing.SwingBuilder
import java.awt.BorderLayout
import java.text.SimpleDateFormat

import static javax.swing.JFrame.EXIT_ON_CLOSE

class AppGroovy {
    static void main(String[] args) {
        // defaults
        def localita = new Localita(codiceFiscale: "F205")
        def persona = new Persona(
                nome: "TEST",
                cognome: "TEST",
                sesso: "M",
                dataNascita: new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1990"),
                localita: localita
        )

        def swingBuilder = new SwingBuilder()

        swingBuilder.edt {
            //lookAndFeel 'nimbus'
            frame(title: 'Codice Fiscale',
                    size: [400, 450],
                    show: true,
                    resizable: false,
                    locationRelativeTo: null,
                    defaultCloseOperation: EXIT_ON_CLOSE) {

                borderLayout(vgap: 6)

                panel(constraints: BorderLayout.NORTH,
                        border: compoundBorder([emptyBorder(5), titledBorder('Dati Anagrafici')])) {
                    tableLayout {
                        tr {
                            td { label 'Nome:' }
                            td { textField id: 'nomeField', columns: 20, text: persona.nome }
                        }
                        tr {
                            td { label 'Cognome:' }
                            td { textField id: 'cognomeField', columns: 20, text: persona.cognome }
                        }
                        tr {
                            td { label 'Sesso:' }
                            td { textField id: 'sessoField', columns: 2, text: persona.sesso }
                        }
                        tr {
                            td { label 'Data Nascita:' }
                            td { textField id: 'dataNascitaField', columns: 8, text: persona.dataNascitaString }
                        }
                        tr {
                            td { label 'Localita Nascita:' }
                            td { textField id: 'localitaField', columns: 20, text: localita.codiceFiscale }
                        }

                    }
                }

                panel(constraints: BorderLayout.CENTER,
                        border: compoundBorder([emptyBorder(5), titledBorder('Codice Fiscale')])) {
                    tableLayout {
                        tr {
                            td { label 'Codice Fiscale:' }
                            td { textField id: 'codiceFiscaleField', columns: 20, editable: false, text: persona.codiceFiscale }
                        }
                    }
                    scrollPane(constraints: BorderLayout.CENTER) {
                        textArea(id: 'omoTextArea', lineWrap: true, wrapStyleWord: true, columns: 28, rows: 6, editable: false)
                    }

                }

                panel(constraints: BorderLayout.SOUTH) {
                    button text: 'Genera', actionPerformed: {
                        def codici = CodiceFiscaleChecker.getAllValidCodiciFiscali(persona)
                        persona.codiceFiscale = codici[0]
                        codiceFiscaleField.text = persona.codiceFiscale
                        omoTextArea.text = codici
                        println persona
                    }
                }

                bean persona,
                        nome: bind { nomeField.text },
                        cognome: bind { cognomeField.text },
                        sesso: bind { sessoField.text },
                        codiceFiscale: bind { codiceFiscaleField.text },
                        dataNascitaString: bind { dataNascitaField.text }

                bean localita, codiceFiscale: bind { localitaField.text }
            }
        }
    }
}
