package edu.washington.yujia1.quizdroid2;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;



public class answer extends android.support.v4.app.Fragment {

    private QuestionClass question;
    private QuestionActivity hostActivity;
    int answerNo;
    int count;
    public answer(QuestionClass question) {
        this.question = question;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("answerNo") && getArguments().containsKey("count")) {
            answerNo = getArguments().getInt("answerNo");
            count = getArguments().getInt("count");

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer, container, false);
        String answerStr = new String();
        String corStr = new String();
        TextView txtAnswer = (TextView) view.findViewById(R.id.txtAnswer);
        TextView txtCor = (TextView) view.findViewById(R.id.txtCor);
        TextView txtCount = (TextView) view.findViewById(R.id.txtCount);


        switch (question.getCorrectAnswer()){
            case 1:
                corStr = question.getAnswer1();
                break;
            case 2:
                corStr = question.getAnswer2();
                break;
            case 3:
                corStr = question.getAnswer3();
                break;
            case 4:
                corStr = question.getAnswer4();
                break;
        }
        txtCor.setText("The correct answer is: " + corStr);
        txtCount.setText("So far you got " + count + " out of 1 correct!");
        switch (answerNo){
            case 1:
                answerStr = question.getAnswer1();
                break;
            case 2:
                answerStr = question.getAnswer2();
                break;
            case 3:
                answerStr = question.getAnswer3();
                break;
            case 4:
                answerStr = question.getAnswer4();
                break;
        }
        txtAnswer.setText("Your answer is: " + answerStr);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        QuestionActivity qa = (QuestionActivity)activity;
        hostActivity = qa;
    }





    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
