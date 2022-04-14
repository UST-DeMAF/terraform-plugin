package ust.tad.terraformplugin.analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ust.tad.terraformplugin.analysistask.AnalysisTaskResponseSender;
import ust.tad.terraformplugin.analysistask.Location;
import ust.tad.terraformplugin.models.ModelsService;
import ust.tad.terraformplugin.models.tadm.annotatedentities.AnnotatedDeploymentModel;
import ust.tad.terraformplugin.models.tsdm.TechnologySpecificDeploymentModel;

@Service
public class AnalysisService {

    @Autowired
    ModelsService modelsService;

    @Autowired
    AnalysisTaskResponseSender analysisTaskResponseSender;
    
    private TechnologySpecificDeploymentModel tsdm;

    private AnnotatedDeploymentModel tadm;

    private Set<Integer> newEmbeddedDeploymentModelIndexes = new HashSet<>();

    /**
     * Start the analysis of the deployment model.
     * 1. Retrieve internal deployment models from models service
     * 2. Parse in technology-specific deployment model from locations
     * 3. Update tsdm with new information
     * 4. Transform to EDMM entities and update tadm
     * 5. Send updated models to models service
     * 6. Send AnalysisTaskResponse or EmbeddedDeploymentModelAnalysisRequests if present 
     * 
     * @param taskId
     * @param transformationProcessId
     * @param commands
     * @param locations
     */
    public void startAnalysis(UUID taskId, UUID transformationProcessId, List<String> commands, List<Location> locations) {
        this.tsdm = modelsService.getTechnologySpecificDeploymentModel(transformationProcessId);
        this.tadm = modelsService.getTechnologyAgnosticDeploymentModel(transformationProcessId);

        try {
            runAnalysis();
        } catch (Exception e) { //TODO change to more specific Exception classes
            e.printStackTrace();
            analysisTaskResponseSender.sendFailureResponse(taskId, e.getClass()+": "+e.getMessage());
            return;
        }

        updateDeploymentModels(this.tsdm, this.tadm);

        if(newEmbeddedDeploymentModelIndexes.isEmpty()) {
            analysisTaskResponseSender.sendSuccessResponse(taskId);
        } else {
            for (int index : newEmbeddedDeploymentModelIndexes) {
                analysisTaskResponseSender.sendEmbeddedDeploymentModelAnalysisRequestFromModel(
                    this.tsdm.getEmbeddedDeploymentModels().get(index), taskId); 
            }
            analysisTaskResponseSender.sendSuccessResponse(taskId);
        }
    }

    private void runAnalysis() {
        //TODO Analysis and Transformation Logic goes here
    }

    private void updateDeploymentModels(TechnologySpecificDeploymentModel tsdm, AnnotatedDeploymentModel tadm) {
        modelsService.updateTechnologySpecificDeploymentModel(tsdm);
        modelsService.updateTechnologyAgnosticDeploymentModel(tadm);
    }

    
}
