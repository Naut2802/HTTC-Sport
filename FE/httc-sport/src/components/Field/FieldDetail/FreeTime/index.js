import * as React from "react";
import Tooltip from "@mui/material/Tooltip";
import { Typography } from "@mui/material";
import { Link } from "react-router-dom";

function ThoiGianTrong() {
  return (
    <div className="d-flex justify-content-center">
      <Tooltip title="Đang Đá">
        <Typography className="text-decoration-none text-dark mx-2" noWrap component="span">
          00:00 - 01:00
        </Typography>
      </Tooltip>
      <Tooltip title="Trống">
        <Typography className="text-decoration-none mx-2" noWrap component={Link} to="/home">
          00:00 - 01:00
        </Typography>
      </Tooltip>
    </div>
  );
}

export default ThoiGianTrong;
