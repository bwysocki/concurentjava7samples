import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class SampleCustomTask extends DefaultTask {

	def msg = "test"
	
	@TaskAction
	def runQuery(){
		println msg
	}
	
}
