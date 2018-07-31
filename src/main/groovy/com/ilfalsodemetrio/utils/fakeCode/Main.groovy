package com.ilfalsodemetrio.utils.fakeCode

import groovy.beans.Bindable
import groovy.swing.SwingBuilder
import it.lispa.siss.nar.commons.entity.Comune
import it.lispa.siss.nar.commons.entity.LocalitaNascita
import it.lispa.siss.nar.commons.entity.Persona
import it.lispa.siss.nar.commons.repository.persona.impl.CodiceFiscaleChecker

import java.awt.*

import static javax.swing.JFrame.EXIT_ON_CLOSE

@Bindable
class ComuneG extends Comune {
    String toString() { "comune=$codiceFiscale" }

}
@Bindable
class LocalitaNascitaG extends LocalitaNascita {
    String toString() { "localitaNascita$comune" }
}

@Bindable
class PersonaG extends Persona {
    String toString() {
        "nome=$nome,cognome=$cognome,sesso=$sesso,dataNascitaString=$dataNascitaString,localitaNascita=$localitaNascita"
    }
}

def comune = new ComuneG(codiceFiscale: "F205")
def localitaNascita = new LocalitaNascitaG(comune: comune)
def persona = new PersonaG(
        nome: "TEST",
        cognome: "TEST",
        sesso: "M",
        dataNascitaString: "01/01/1990",
        localitaNascita: localitaNascita
)

def swingBuilder = new SwingBuilder()

swingBuilder.edt {
    //lookAndFeel 'nimbus'
    frame(title: 'CF',
            size: [400, 400],
            show: true,
            locationRelativeTo: null,
            defaultCloseOperation: EXIT_ON_CLOSE) {

        borderLayout(vgap: 6)

        panel(constraints: BorderLayout.CENTER, border: compoundBorder([emptyBorder(10), titledBorder()])) {

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
                    td { textField id: 'localitaField', columns: 20, text: comune.codiceFiscale }
                }
                tr {
                    td { label 'Codice Fiscale:' }
                    td { textField id: 'codiceFiscaleField', columns: 20, text: persona.codiceFiscale }
                }
            }
            panel(constraints: BorderLayout.SOUTH){
                scrollPane(constraints: BorderLayout.CENTER){
                    textArea(id:'omoTextArea', lineWrap:true,wrapStyleWord:true, columns:25, rows:5, editable:false)
                }
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

        bean comune, codiceFiscale: bind { localitaField.text }
    }
}
