import React from 'react';
import { Breadcrumbs, Input, Link } from '@mui/material';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';
import { Bar } from 'react-chartjs-2';
import { faker } from '@faker-js/faker';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const currentYear = new Date().getFullYear();
export const options = {
    responsive: true,
    plugins: {
        legend: {
            position: 'top',
        },
        title: {
            display: true,
            text: 'Thống Kê Doanh Thu Năm ' + currentYear,
        },
    },
};

export const options_1 = {
    responsive: true,
    plugins: {
        legend: {
            position: 'top',
        },
        title: {
            display: true,
            text: 'Thống Kê Học Viên Năm ' + currentYear,
        },
    },
};

export const options_2 = {
    responsive: true,
    plugins: {
        legend: {
            position: 'top',
        },
        title: {
            display: true,
            text: 'Thống Kê...... ' + currentYear,
        },
    },
};

const labels = ['T1', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'T8', 'T9', 'T10', 'T11', 'T12'];

export const data = {
    labels,
    datasets: [
        {
            label: 'Doanh Thu ',
            data: labels.map(() => faker.datatype.number({ min: 10000000, max: 500000000 })),
            backgroundColor: 'rgba(255, 99, 132, 0.5)',
        },
    ],
};

function ThongKe() {
    return (
        <div className="row">
            <div className="col-2"></div>
            <div className="col-8 border rounded-2 my-4 d-flex justify-content-center mt-2 ">
                <div className="w-75 bg-white mx-2">
                    <Bar options={options} data={data} />
                    <div className="float-end fs-5 m-2">Doanh Thu: 123123123123</div>
                </div>
            </div>
        </div>
    );
}

export default ThongKe;
