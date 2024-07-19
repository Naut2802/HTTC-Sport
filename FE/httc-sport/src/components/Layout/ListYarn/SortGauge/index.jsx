import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { Checkbox, Typography } from '@mui/material';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormGroup from '@mui/material/FormGroup';

export default function SortGauge() {
    return (
        <Accordion className="mb-2">
            <AccordionSummary expandIcon={<ExpandMoreIcon />} aria-controls="panel2-content" id="panel2-header">
                <Typography className="fs-5">Loại Sân</Typography>
            </AccordionSummary>
            <AccordionDetails>
                <FormGroup>
                    <FormControlLabel control={<Checkbox defaultChecked />} label="5 Người" />
                    <FormControlLabel control={<Checkbox />} label="7 Người" />
                </FormGroup>
            </AccordionDetails>
        </Accordion>
    );
}