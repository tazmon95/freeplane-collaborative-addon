// @ExecutionModes({ON_SINGLE_NODE})

//�Freeplane collaborative add-on
//
//�Groovy script to execute version control system (CVS) actions
//���������
//�Copyright (c)2011 Auxilus Systems LLC
//
//�This program is free software: you can redistribute it and/or modify
//�it under the terms of the GNU General Public License as published by
//�the Free Software Foundation, either version 3 of the License, or
//�any later version.
//
//�This program is distributed in the hope that it will be useful,
//�but WITHOUT ANY WARRANTY; without even the implied warranty of
//�MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.��See the
//�GNU General Public License for more details.
//
//�You should have received a copy of the GNU General Public License
//�along with this program.��If not, see <http://www.gnu.org/licenses/>.


import javax.swing.JDialog;
import javax.swing.JOptionPane

///////////
// Params
///////////

// todo : move to add-on attributes
// todo : check existence + alert
def vcsBin = "C:/Program Files (x86)/CVSNT/cvs.exe"



//////////
// Main
//////////

// todo : create a .bak file in case something goes wrong

def initialSize = 4096
def outStream = new ByteArrayOutputStream(initialSize)
def errStream = new ByteArrayOutputStream(initialSize)
def vcsCommandArray = [vcsBin, "diff", node.map.file.name]
def processBuilder = new ProcessBuilder(vcsCommandArray)
	.directory(new File(node.map.file.getParent()))
	.redirectErrorStream(true)
def vcsProcess = processBuilder.start() //[], new File(node.map.file.getParent()))
vcsProcess.consumeProcessOutput(outStream, errStream)
vcsProcess.waitFor()

def message = "Executed:\n" + vcsCommandArray.join(" ")

if (outStream.size() > 0) {
	message += "\n\nResult:\n" + outStream
} else {
	message += "\n\nResult:\n  no diff" 
}
	
if (errStream.size() > 0)
	message += "\n\nErrors: \n" + errStream

// todo : translation 
JOptionPane.showMessageDialog(ui.frame, 
	message,
	"vcs diff", JOptionPane.INFORMATION_MESSAGE)
 