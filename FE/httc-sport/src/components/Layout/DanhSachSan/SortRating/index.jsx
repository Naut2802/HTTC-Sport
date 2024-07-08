import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { Checkbox, Typography } from '@mui/material';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormGroup from '@mui/material/FormGroup';

function SortRating() {
    return (
        <Accordion className="my-2">
            <AccordionSummary expandIcon={<ExpandMoreIcon />} aria-controls="panel1-content" id="panel1-header">
                <Typography className="fs-5">Đánh Giá</Typography>
            </AccordionSummary>
            <AccordionDetails>
                <FormGroup>
                    <FormControlLabel control={<Checkbox defaultChecked />} label="5 Sao" />
                    <FormControlLabel control={<Checkbox />} label="4 Sao" />
                    <FormControlLabel control={<Checkbox />} label="3 Sao" />
                    <FormControlLabel control={<Checkbox />} label="2 Sao" />
                    <FormControlLabel control={<Checkbox />} label="1 Sao" />
                </FormGroup>
            </AccordionDetails>
        </Accordion>
    );
}

export default SortRating;
